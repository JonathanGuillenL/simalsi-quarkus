package org.lab.simalsi.factura.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import net.sf.jasperreports.engine.*;
import org.lab.simalsi.cliente.infrastructure.ClienteRepository;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.cliente.models.TipoCliente;
import org.lab.simalsi.colaborador.infrastructure.ColaboradorRepository;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.common.GeneralErrorException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.infrastructure.*;
import org.lab.simalsi.factura.models.*;
import org.lab.simalsi.persona.models.Persona;
import org.lab.simalsi.persona.models.PersonaJuridica;
import org.lab.simalsi.persona.models.PersonaNatural;
import org.lab.simalsi.servicio.infrastructure.ServicioLaboratorioRepository;

import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

@ApplicationScoped
public class FacturaService {

    @Inject
    FacturaRepository facturaRepository;

    @Inject
    FacturaMapper facturaMapper;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ColaboradorRepository colaboradorRepository;

    @Inject
    DescuentoRepository descuentoRepository;

    @Inject
    DetalleFacturaRepository detalleFacturaRepository;

    @Inject
    private MonedaRepository monedaRepository;

    @Inject
    private MetodoPagoRepository metodoPagoRepository;

    @Inject
    private PagoMapper pagoMapper;

    public PageDto<FacturaPageResponseDto> listarFacturasByCliente(int page, int size, String userId, FacturaQueryDto facturaQueryDto) {
        Cliente cliente = clienteRepository.findByUsername(userId)
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado."));
        facturaQueryDto.clienteId = cliente.getId();
        return listarFacturas(page, size, facturaQueryDto);
    }

    public PageDto<FacturaPageResponseDto> listarFacturas(int page, int size, FacturaQueryDto facturaQueryDto) {
        PanacheQuery<Factura> query = facturaRepository.findByQueryDto(facturaQueryDto);
        List<FacturaPageResponseDto> lista = query.page(Page.of(page, size))
            .stream()
            .map(facturaMapper::toPageResponse)
            .toList();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public FacturaResponseDto obtenerFacturaPorId(Long id) {
        Factura factura = facturaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Factura no encontrada."));
        return facturaMapper.toResponse(factura);
    }

    public String generateToken(Long facturaId, String userId, long amountToAdd, TemporalUnit unit) {
        if (amountToAdd <= 0 || unit == null) {
            throw new IllegalArgumentException();
        }

        Instant now = Instant.now();

        return Jwt.claims()
            .subject(userId)
            .claim("factura_id", facturaId)
            .issuedAt(now)
            .expiresAt(now.plus(amountToAdd, unit))
            .sign();
    }

    public Factura registrarFactura(String userId, CrearFacturaDto facturaDto) {
        Colaborador recepcionista = colaboradorRepository.findColaboradorByUsername(userId)
            .orElseThrow(() -> new NotFoundException("Colaborador con userId no encontrado"));

        Cliente cliente = clienteRepository.findByIdOptional(facturaDto.clienteId())
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        if (facturaDto.detalle() == null || facturaDto.detalle().isEmpty()) {
            throw new GeneralErrorException("La factura debe tener al menos un servicio asociado.");
        }

        List<DescuentoFactura> descuentoFacturas = facturaDto.descuentos().stream().map(descuentoId -> {
            Descuento descuento = descuentoRepository.findById(descuentoId);

            if (descuento == null) {
                throw new NotFoundException("Descuento no encontrado");
            }

            if (!descuento.isDisponible(LocalDate.now())) {
                throw new NotFoundException("Descuento no esta disponible para la fecha de solicitada");
            }

            DescuentoFactura descuentoFactura = new DescuentoFactura();
            descuentoFactura.setPorcentaje(descuento.getPorcentaje());
            descuentoFactura.setDescuento(descuento);
            descuentoFactura.setFechaAplica(LocalDateTime.now());

            return descuentoFactura;
        }).toList();

        List<DetalleFactura> detalleFacturas = facturaDto.detalle().stream().map(detalleId -> {
            DetalleFactura detalleFactura = detalleFacturaRepository.findById(detalleId);

            if (detalleFactura.isFacturado()) {
                throw new NotFoundException("Detalle ya ha sido facturado");
            }

            detalleFactura.setFacturado(true);
            detalleFacturaRepository.persist(detalleFactura);

            return detalleFactura;
        }).toList();

        Factura factura = new Factura();
        factura.setRecepcionista(userId);
        factura.setCliente(cliente);
        factura.setDescuentos(descuentoFacturas);
        factura.setDetalle(detalleFacturas);

        if (facturaDto.pago() != null) {
            PagoDto pagoDto = facturaDto.pago();
            Moneda moneda = monedaRepository.findByIdOptional(pagoDto.monedaId())
                .orElseThrow(() -> new NotFoundException("Tipo de moneda no encontrada."));

            MetodoPago metodoPago = metodoPagoRepository.findByIdOptional(pagoDto.metodoPagoId())
                .orElseThrow(() -> new NotFoundException("Metodo de pago no encontrado."));

            Pago pago = pagoMapper.toModel(pagoDto);
            pago.setTipoCambio(moneda.getTipoCambio());
            pago.setMoneda(moneda);
            pago.setMetodoPago(metodoPago);

            if (pago.calcularMontoCambio() > factura.calcularSaldoPendiente()) {
                throw new GeneralErrorException("Monto de pago es mayor al saldo pendiente.");
            }
            if (cliente.getTipoCliente() == TipoCliente.CLIENTE_ESPONTANEO && !Objects.equals(pago.calcularMontoCambio(), factura.calcularSaldoPendiente())) {
                throw new GeneralErrorException("Cliente espontáneo debe completar el pago de la factura.");
            }

            factura.addPago(pago);
        } else {
            if (cliente.getTipoCliente() == TipoCliente.CLIENTE_ESPONTANEO) {
                throw new GeneralErrorException("La factura debe tener una pago asociado.");
            }
        }

        factura.calcularTotales();
        factura.calcularSaldoPendiente();

        facturaRepository.persist(factura);

        return factura;
    }

    public byte[] generarFacturaPdf(Long facturaId) {
        HashMap<String, Object> params = new HashMap<>();

        Factura factura = facturaRepository.findByIdOptional(facturaId)
            .orElseThrow(() -> new NotFoundException("Factura no encontrada"));

        Persona persona = factura.getCliente().getPersona();

        params.put("telefono", persona.getTelefono());

        if (persona instanceof PersonaJuridica personaJuridica) {
            params.put("cliente", personaJuridica.getNombre());
        } else if (persona instanceof PersonaNatural personaNatural) {
            params.put("cliente", personaNatural.getNombre() + " " + personaNatural.getApellido());
        }

        Double total = factura.calcularTotales();
        Double subtotal = factura.getSubtotal();
        Double descuento = factura.getDescuento();
        Colaborador recepcionista = colaboradorRepository.findColaboradorByUsername(factura.getRecepcionista())
            .orElseThrow(() -> new NotFoundException("Recepcionista no encontrado"));
        Date fechaSolicitud = Date.from(factura.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant());

        params.put("signoMonetario", "C$");
        params.put("creado_en", fechaSolicitud);
        params.put("recepcionista", recepcionista.getFullname());
        params.put("factura_id", factura.getId());
        params.put("subtotal", subtotal);
        params.put("descuento", subtotal * descuento / 100);
        params.put("total", factura.getTotal());

        FacturaJRDataSource dataSource = new FacturaJRDataSource(factura.getDetalle());

         ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("reportes/factura.jasper")) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException | IOException e) {
            Log.error(e.getMessage());
            Log.error(e.getCause());
            return null;
        }
    }
}

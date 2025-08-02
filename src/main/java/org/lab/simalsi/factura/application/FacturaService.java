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
import org.lab.simalsi.colaborador.infrastructure.ColaboradorRepository;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.infrastructure.*;
import org.lab.simalsi.factura.models.*;
import org.lab.simalsi.persona.models.Persona;
import org.lab.simalsi.persona.models.PersonaJuridica;
import org.lab.simalsi.persona.models.PersonaNatural;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FacturaService {

    @Inject
    FacturaRepository facturaRepository;

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

    public PageDto<Factura> listarFacturas(int page, int size) {
        PanacheQuery<Factura> query = facturaRepository.findAll();
        List<Factura> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public Factura obtenerFacturaPorId(Long id) {
        return facturaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Factura no encontrada."));
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

        if (!recepcionista.getCargo().getNombre().equals("Recepcionista")) {
            throw new NotFoundException("Colaborador no tiene asignado cargo Recepcionista");
        }

        Cliente cliente = clienteRepository.findByIdOptional(facturaDto.clienteId())
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

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
        factura.setRecepcionista(recepcionista);
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
            pago.setCambio(moneda.getTipoCambio());
            pago.setMoneda(moneda);
            pago.setMetodoPago(metodoPago);

            factura.addPago(pago);
        }

        factura.calcularDescuento();
        factura.calcularSubtotal();

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

        Double subtotal = factura.calcularSubtotal();
        Double descuento = factura.calcularDescuento();

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

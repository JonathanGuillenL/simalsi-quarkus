package org.lab.simalsi.factura.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.cliente.infrastructure.ClienteRepository;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.infrastructure.DescuentoRepository;
import org.lab.simalsi.factura.infrastructure.DetalleFacturaRepository;
import org.lab.simalsi.factura.infrastructure.FacturaRepository;
import org.lab.simalsi.factura.models.Descuento;
import org.lab.simalsi.factura.models.DescuentoFactura;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.factura.models.Factura;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FacturaService {

    @Inject
    FacturaRepository facturaRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    DescuentoRepository descuentoRepository;

    @Inject
    DetalleFacturaRepository detalleFacturaRepository;

    public PageDto<Factura> listarFacturas(int page, int size) {
        PanacheQuery<Factura> query = facturaRepository.findAll();
        List<Factura> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public Factura registrarFactura(CrearFacturaDto facturaDto) {
        Cliente cliente = clienteRepository.findById(facturaDto.clienteId());

        if (cliente == null) {
            throw new NotFoundException("Cliente no encontrado");
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
        factura.setCliente(cliente);
        factura.setDescuentos(descuentoFacturas);
        factura.setDetalle(detalleFacturas);

        facturaRepository.persist(factura);

        return factura;
    }
}

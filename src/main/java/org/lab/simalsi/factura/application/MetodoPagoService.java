package org.lab.simalsi.factura.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.infrastructure.MetodoPagoRepository;
import org.lab.simalsi.factura.models.MetodoPago;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class MetodoPagoService {

    @Inject
    private MetodoPagoRepository metodoPagoRepository;

    public PageDto<MetodoPago> obtenerPageMetodoPago(int page, int size, MetodoPagoQueryDto metodoPagoQueryDto) {
        PanacheQuery<MetodoPago> query = metodoPagoRepository.findByQueryDto(metodoPagoQueryDto);
        List<MetodoPago> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public List<MetodoPago> obtenerListMetodoPago() {
        return metodoPagoRepository.findAll().list();
    }

    public MetodoPago obtenerMetodoPagoPorId(Long id) {
        return metodoPagoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Método de pago no encontrado."));
    }

    public MetodoPago registrarMetodoPago(CrearMetodoPagoDto metodoPagoDto) {
        MetodoPago metodoPago = new MetodoPago(metodoPagoDto.descripcion());

        metodoPagoRepository.persist(metodoPago);
        return metodoPago;
    }

    public MetodoPago actualizarMetodoPago(Long id, CrearMetodoPagoDto metodoPagoDto) {
        MetodoPago metodoPago = metodoPagoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Método de pago no encontrado."));

        metodoPago.setDescripcion(metodoPagoDto.descripcion());

        metodoPagoRepository.persist(metodoPago);
        return metodoPago;
    }

    public void activarMetodoPago(Long id) {
        MetodoPago metodoPago = metodoPagoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Método de pago no encontrado"));

        metodoPago.setDeletedAt(null);
        metodoPagoRepository.persist(metodoPago);
    }

    public void desactivarMetodoPago(Long id) {
        MetodoPago metodoPago = metodoPagoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Método de pago no encontrado."));

        metodoPago.setDeletedAt(LocalDateTime.now());
        metodoPagoRepository.persist(metodoPago);
    }
}

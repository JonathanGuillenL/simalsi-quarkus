package org.lab.simalsi.factura.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.infrastructure.MetodoPagoRepository;
import org.lab.simalsi.factura.models.MetodoPago;

import java.util.List;

@ApplicationScoped
public class MetodoPagoService {

    @Inject
    private MetodoPagoRepository metodoPagoRepository;

    public PageDto<MetodoPago> obtenerPageMetodoPago(int page, int size) {
        PanacheQuery<MetodoPago> query = metodoPagoRepository.findAll();
        List<MetodoPago> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public List<MetodoPago> obtenerListMetodoPago() {
        return metodoPagoRepository.findAll().list();
    }

    public MetodoPago registrarMetodoPago(CrearMetodoPagoDto metodoPagoDto) {
        MetodoPago metodoPago = new MetodoPago(metodoPagoDto.descripcion());

        metodoPagoRepository.persist(metodoPago);
        return metodoPago;
    }
}

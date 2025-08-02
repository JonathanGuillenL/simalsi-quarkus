package org.lab.simalsi.factura.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.infrastructure.DescuentoRepository;
import org.lab.simalsi.factura.models.Descuento;

import java.util.List;

@ApplicationScoped
public class DescuentoService {

    @Inject
    private DescuentoRepository descuentoRepository;

    @Inject
    private DescuentoMapper descuentoMapper;

    public PageDto<Descuento> obtenerPaginaDescuentos(int page, int size) {
        PanacheQuery<Descuento> query = descuentoRepository.findAll();
        List<Descuento> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public Descuento obtenerDescuentoPorId(Long id) {
        return descuentoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Descuento no encontrado."));
    }

    public Descuento registrarDescuento(CrearDescuentoDto descuentoDto) {
        Descuento descuento = descuentoMapper.toModel(descuentoDto);

        descuentoRepository.persist(descuento);
        return descuento;
    }
}

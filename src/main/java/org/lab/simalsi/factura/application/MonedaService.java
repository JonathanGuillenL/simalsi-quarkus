package org.lab.simalsi.factura.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.infrastructure.MonedaRepository;
import org.lab.simalsi.factura.models.Moneda;

import java.util.List;

@ApplicationScoped
public class MonedaService {

    @Inject
    private MonedaRepository monedaRepository;

    @Inject
    private MonedaMapper monedaMapper;

    public PageDto<Moneda> obtenerPageMonedas(int page, int size) {
        PanacheQuery<Moneda> query = monedaRepository.findAll();
        List<Moneda> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public List<Moneda> obtenerListMonedas() {
        return monedaRepository.findAll().list();
    }

    public Moneda registrarMoneda(CrearMonedaDto monedaDto) {
        Moneda moneda = monedaMapper.toModel(monedaDto);

        monedaRepository.persist(moneda);
        return moneda;
    }

    public Moneda obtenerMonedaPorId(Long id) {
        return monedaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Moneda no encontrada."));
    }
}

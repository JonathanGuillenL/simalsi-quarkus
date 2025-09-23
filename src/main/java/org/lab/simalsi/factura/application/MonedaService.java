package org.lab.simalsi.factura.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.infrastructure.MonedaRepository;
import org.lab.simalsi.factura.models.Moneda;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class MonedaService {

    @Inject
    private MonedaRepository monedaRepository;

    @Inject
    private MonedaMapper monedaMapper;

    public PageDto<Moneda> obtenerPageMonedas(int page, int size, MonedaQueryDto monedaQueryDto) {
        PanacheQuery<Moneda> query = monedaRepository.findByQueryDto(monedaQueryDto);
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

    public Moneda actualizarMoneda(Long id, CrearMonedaDto monedaDto) {
        Moneda moneda = monedaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Moneda no encontrada."));

        moneda.setDescripcion(monedaDto.descripcion());
        moneda.setTipoCambio(monedaDto.tipoCambio());
        moneda.setSignoMonetario(monedaDto.signoMonetario());

        monedaRepository.persist(moneda);
        return moneda;
    }

    public void activarMoneda(Long id) {
        Moneda moneda = monedaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Moneda no encontrada."));

        moneda.setDeletedAt(null);
        monedaRepository.persist(moneda);
    }

    public void desactivarMoneda(Long id) {
        Moneda moneda = monedaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Moneda no encontrada."));

        moneda.setDeletedAt(LocalDateTime.now());
        monedaRepository.persist(moneda);
    }
}

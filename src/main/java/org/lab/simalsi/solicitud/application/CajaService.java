package org.lab.simalsi.solicitud.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.solicitud.infrastructure.CajaRepository;
import org.lab.simalsi.solicitud.infrastructure.LaminaRepository;
import org.lab.simalsi.solicitud.models.Caja;
import org.lab.simalsi.solicitud.models.Lamina;

import java.util.List;

@ApplicationScoped
public class CajaService {

    @Inject
    private CajaRepository cajaRepository;

    @Inject
    private LaminaRepository laminaRepository;

    public PageDto<CajaResponseDto> obtenerPageCaja(int page, int size) {
        PanacheQuery<Caja> query = cajaRepository.findAll();
        List<CajaResponseDto> lista = query.page(Page.of(page, size))
            .stream()
            .map(caja -> new CajaResponseDto(caja.getId(), caja.getNumeroColumnas(), caja.getNumeroFilas()))
            .toList();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public List<CajaResponseDto> obtenerListCajas() {
        List<Caja> cajas = cajaRepository.listAll();
        return cajas.stream()
            .map(caja -> new CajaResponseDto(caja.getId(), caja.getNumeroColumnas(), caja.getNumeroFilas()))
            .toList();
    }

    public List<LaminaResponseDto> obtenerLaminasPorCajaId(Long id) {
        return laminaRepository.find("caja.id", id)
            .stream()
            .map(lamina -> new LaminaResponseDto(lamina.getId(), lamina.getFila(), lamina.getColumna(), lamina.getCaja().getId()))
            .toList();
    }

    public Caja registrarCaja(CrearCajaDto cajaDto) {
        Caja caja = new Caja();
        caja.setNumeroColumnas(cajaDto.numeroColumnas());
        caja.setNumeroFilas(cajaDto.numeroFilas());

        cajaRepository.persist(caja);
        return caja;
    }
}

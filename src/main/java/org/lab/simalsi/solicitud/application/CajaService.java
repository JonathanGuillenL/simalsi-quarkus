package org.lab.simalsi.solicitud.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.solicitud.infrastructure.CajaRepository;
import org.lab.simalsi.solicitud.models.Caja;

import java.util.List;

@ApplicationScoped
public class CajaService {

    @Inject
    private CajaRepository cajaRepository;

    public PageDto<Caja> obtenerPageCaja(int page, int size) {
        PanacheQuery<Caja> query = cajaRepository.findAll();
        List<Caja> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public List<Caja> obtenerListCajas() {
        return cajaRepository.findAll().list();
    }

    public Caja registrarCaja(CrearCajaDto cajaDto) {
        Caja caja = new Caja();
        caja.setNumeroColumnas(cajaDto.numeroColumnas());
        caja.setNumeroFilas(cajaDto.numeroFilas());

        cajaRepository.persist(caja);
        return caja;
    }
}

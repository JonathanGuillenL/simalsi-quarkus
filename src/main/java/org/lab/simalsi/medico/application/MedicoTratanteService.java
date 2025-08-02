package org.lab.simalsi.medico.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.medico.infrastructure.MedicoTratanteRepository;
import org.lab.simalsi.medico.models.MedicoTratante;

import java.util.List;

@ApplicationScoped
public class MedicoTratanteService {

    @Inject
    private MedicoTratanteRepository medicoTratanteRepository;

    @Inject
    private MedicoTratanteMapper medicoTratanteMapper;

    public PageDto<MedicoTratante> obtenerListaMedicosTrantantes(int page, int size) {
        PanacheQuery<MedicoTratante> query = medicoTratanteRepository.findAll();
        List<MedicoTratante> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public MedicoTratante obtenerMedicoPorId(Long id) {
        return medicoTratanteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Medico tratante no encontrado."));
    }

    public MedicoTratante registrarMedicoTratante(CrearMedicoTratante medicoTratanteDto) {
        MedicoTratante medicoTratante = medicoTratanteMapper.toModel(medicoTratanteDto);

        medicoTratanteRepository.persist(medicoTratante);
        return medicoTratante;
    }
}

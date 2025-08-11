package org.lab.simalsi.medico.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.medico.infrastructure.MedicoTratanteRepository;
import org.lab.simalsi.medico.models.MedicoTratante;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MedicoTratanteService {

    @Inject
    private MedicoTratanteRepository medicoTratanteRepository;

    @Inject
    private MedicoTratanteMapper medicoTratanteMapper;

    public PageDto<MedicoTratanteResponsePageDto> obtenerListaMedicosTrantantes(int page, int size, MedicoTratanteQueryDto medicoTratanteQueryDto) {
        PanacheQuery<MedicoTratante> query = medicoTratanteRepository.findByQueryDto(medicoTratanteQueryDto);
        List<MedicoTratanteResponsePageDto> lista = query.page(Page.of(page, size))
            .stream()
            .map(medicoTratanteMapper::toResponsePage)
            .collect(Collectors.toList());
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public MedicoTratanteResponseDto obtenerMedicoPorId(Long id) {
        return medicoTratanteRepository.findByIdOptional(id)
            .map(medicoTratanteMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Medico tratante no encontrado."));
    }

    public MedicoTratante registrarMedicoTratante(CrearMedicoTratante medicoTratanteDto) {
        boolean existsCodigoSanitario = medicoTratanteRepository.existsByCodigoSanitario(medicoTratanteDto.codigoSanitario());
        if (existsCodigoSanitario) {
            throw new RuntimeException("Código sanitario ya ha sido registrado.");
        }

        MedicoTratante medicoTratante = medicoTratanteMapper.toModel(medicoTratanteDto);

        medicoTratanteRepository.persist(medicoTratante);
        return medicoTratante;
    }
}

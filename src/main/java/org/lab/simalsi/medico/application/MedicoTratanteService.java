package org.lab.simalsi.medico.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.DuplicateFieldException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.medico.infrastructure.MedicoTratanteRepository;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.persona.models.PersonaNatural;

import java.time.LocalDateTime;
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

    public PageDto<MedicoTratanteResponsePageDto> obtenerListaMedicosTrantantesWithoutCliente(int page, int size, MedicoTratanteQueryDto medicoTratanteQueryDto) {
        PanacheQuery<MedicoTratante> query = medicoTratanteRepository.findByQueryDto(medicoTratanteQueryDto, true);
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
            .orElseThrow(() -> new NotFoundException("Médico tratante no encontrado."));
    }

    public MedicoTratanteResponseDto obtenerMedicoPorPersonaId(Long personaId) {
        return medicoTratanteRepository.findByPersonaId(personaId)
            .map(medicoTratanteMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Médico tratante no encontrado."));
    }

    public MedicoTratante registrarMedicoTratante(CrearMedicoTratante medicoTratanteDto) {
        var existsCodigoSanitario = medicoTratanteRepository.existsByCodigoSanitario(medicoTratanteDto.codigoSanitario());
        if (existsCodigoSanitario.isPresent()) {
            throw new DuplicateFieldException("Código sanitario ya ha sido registrado.");
        }

        MedicoTratante medicoTratante = medicoTratanteMapper.toModel(medicoTratanteDto);

        medicoTratanteRepository.persist(medicoTratante);
        return medicoTratante;
    }

    public MedicoTratante actualizarMedicoTratante(Long id, CrearMedicoTratante medicoTratanteDto) {
        var existsCodigoSanitario = medicoTratanteRepository.existsByCodigoSanitario(medicoTratanteDto.codigoSanitario());
        if (existsCodigoSanitario.isPresent() && !existsCodigoSanitario.get().getId().equals(id)) {
            throw new DuplicateFieldException("Código sanitario ya ha sido registrado.");
        }

        MedicoTratante medicoTratante = medicoTratanteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Médico no encontrado."));

        PersonaNatural persona = medicoTratante.getPersona();

        persona.setNombre(medicoTratanteDto.nombres());
        persona.setApellido(medicoTratanteDto.apellidos());
        persona.setTelefono(medicoTratanteDto.telefono());
        persona.setDireccion(medicoTratanteDto.direccion());
        medicoTratante.setCodigoSanitario(medicoTratanteDto.codigoSanitario());

        medicoTratanteRepository.persist(medicoTratante);

        return medicoTratante;
    }

    public void activarMedicoTratante(Long id) {
        MedicoTratante medicoTratante = medicoTratanteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Médico tratante no encontrado"));

        medicoTratante.setDeletedAt(null);
        medicoTratanteRepository.persist(medicoTratante);
    }

    public void desactivarMedicoTratanta(Long id) {
        MedicoTratante medicoTratante = medicoTratanteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Médico tratante no encontrado"));

        medicoTratante.setDeletedAt(LocalDateTime.now());
        medicoTratanteRepository.persist(medicoTratante);
    }
}

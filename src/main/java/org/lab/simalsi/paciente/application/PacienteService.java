package org.lab.simalsi.paciente.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.persona.models.Persona;
import org.lab.simalsi.paciente.infrastructure.PacienteRepository;
import org.lab.simalsi.paciente.models.Paciente;

import java.util.List;

@ApplicationScoped
public class PacienteService {

    @Inject
    PacienteRepository pacienteRepository;

    @Inject
    PacienteMapper pacienteMapper;

    public PageDto<Paciente> obtenerListaPacientes(int page, int size) {
        PanacheQuery<Paciente> query = pacienteRepository.findAll();
        List<Paciente> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public Paciente obtenerPacientePorId(Long id) {
        return pacienteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));
    }

    public Paciente registrarPaciente(CrearPacienteDto pacienteDto) {
        Paciente paciente = pacienteMapper.toModel(pacienteDto);
        pacienteRepository.persist(paciente);

        return paciente;
    }

    public Paciente actualizarPaciente(Long id, ActualizarPacienteDto pacienteDto) {
        Paciente paciente = pacienteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));

        Persona persona = paciente.getPersona();

        paciente.setNacimiento(pacienteDto.nacimiento());
        persona.setTelefono(pacienteDto.telefono());

        pacienteRepository.persist(paciente);

        return paciente;
    }
}

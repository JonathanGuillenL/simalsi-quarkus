package org.lab.simalsi.paciente.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.paciente.application.ActualizarPacienteDto;
import org.lab.simalsi.paciente.application.CrearPacienteDto;
import org.lab.simalsi.paciente.application.PacienteService;
import org.lab.simalsi.paciente.models.Paciente;

@Path("/paciente")
public class PacienteResource {

    @Inject
    PacienteService pacienteService;

    @GET
    public PageDto<Paciente> all(@RestQuery @DefaultValue("0") int page,
                       @RestQuery @DefaultValue("10") int size) {
        return pacienteService.obtenerListaPacientes(page, size);
    }
    
    @GET
    @Path("{id}")
    public Paciente show(@RestPath Long id) {
        return pacienteService.obtenerPacientePorId(id);
    }

    @POST
    @Transactional
    public Paciente store(@Valid CrearPacienteDto pacienteDto) {
        return pacienteService.registrarPaciente(pacienteDto);
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Paciente update(@RestPath Long id, ActualizarPacienteDto pacienteDto) {
        return pacienteService.actualizarPaciente(id, pacienteDto);
    }
}

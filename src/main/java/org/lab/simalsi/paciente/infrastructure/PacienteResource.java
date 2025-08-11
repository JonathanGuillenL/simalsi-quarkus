package org.lab.simalsi.paciente.infrastructure;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.paciente.application.*;
import org.lab.simalsi.paciente.models.Paciente;

@Path("/paciente")
public class PacienteResource {

    @Inject
    PacienteService pacienteService;

    @GET
    public PageDto<PacienteResponsePageDto> all(@RestQuery @DefaultValue("0") int page,
                                                @RestQuery @DefaultValue("10") int size,
                                                PacienteQueryDto pacienteQueryDto) {
        Log.info("variable obtenida " + pacienteQueryDto.id);
        return pacienteService.obtenerListaPacientes(page, size, pacienteQueryDto);
    }
    
    @GET
    @Path("{id}")
    public PacienteResponseDto show(@RestPath Long id) {
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

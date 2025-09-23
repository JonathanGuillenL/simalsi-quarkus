package org.lab.simalsi.paciente.infrastructure;

import io.quarkus.logging.Log;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.paciente.application.*;
import org.lab.simalsi.paciente.models.Paciente;

@Path("/paciente")
public class PacienteResource {

    @Inject
    PacienteService pacienteService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PageDto<PacienteResponsePageDto> all(@RestQuery @DefaultValue("0") int page,
                                                @RestQuery @DefaultValue("10") int size,
                                                PacienteQueryDto pacienteQueryDto) {
        return pacienteService.obtenerListaPacientes(page, size, pacienteQueryDto);
    }

    @GET
    @Path("/no-cliente")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PageDto<PacienteResponsePageDto> noClientes(@RestQuery @DefaultValue("0") int page,
                                                @RestQuery @DefaultValue("10") int size,
                                                PacienteQueryDto pacienteQueryDto) {
        return pacienteService.obtenerListaPacientesSinCliente(page, size, pacienteQueryDto);
    }
    
    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PacienteResponseDto show(@RestPath Long id) {
        return pacienteService.obtenerPacientePorId(id);
    }

    @POST
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Paciente store(@Valid CrearPacienteDto pacienteDto) {
        return pacienteService.registrarPaciente(pacienteDto);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Paciente update(@RestPath Long id, @Valid ActualizarPacienteDto pacienteDto) {
        return pacienteService.actualizarPaciente(id, pacienteDto);
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void enable(@RestPath Long id) {
        pacienteService.activarPaciente(id);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void destroy(@RestPath Long id) {
        pacienteService.desactivarPaciente(id);
    }
}

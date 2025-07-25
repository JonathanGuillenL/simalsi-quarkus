package org.lab.simalsi.paciente.infrastructure;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
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
}

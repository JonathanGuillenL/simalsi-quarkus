package org.lab.simalsi.servicio.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.servicio.application.CrearServicioLaboratorioDto;
import org.lab.simalsi.servicio.application.ServicioLaboratorioService;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;

@Path("/servicio-laboratorio")
public class ServicioLaboratorioResource {

    @Inject
    private ServicioLaboratorioService servicioLaboratorioService;

    @GET
    public PageDto<ServicioLaboratorio> page(@RestQuery @DefaultValue("0") int page,
                                             @RestQuery @DefaultValue("10") int size) {
        return servicioLaboratorioService.obtenerPageServicioLaboratorio(page, size);
    }

    @GET
    @Path("{id}")
    public ServicioLaboratorio show(@RestPath Long id) {
        return servicioLaboratorioService.obtenerServicioLaboratorioPorId(id);
    }

    @POST
    @Transactional
    public ServicioLaboratorio store(CrearServicioLaboratorioDto servicioLaboratorioDto) {
        return servicioLaboratorioService.registrarServicio(servicioLaboratorioDto);
    }
}

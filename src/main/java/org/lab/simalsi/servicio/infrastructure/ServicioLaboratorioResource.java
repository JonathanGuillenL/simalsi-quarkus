package org.lab.simalsi.servicio.infrastructure;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.servicio.application.CrearServicioLaboratorioDto;
import org.lab.simalsi.servicio.application.ServicioLaboratorioService;
import org.lab.simalsi.servicio.application.ServicioQueryDto;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;

@Path("/servicio-laboratorio")
public class ServicioLaboratorioResource {

    @Inject
    private ServicioLaboratorioService servicioLaboratorioService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PageDto<ServicioLaboratorio> page(@RestQuery @DefaultValue("0") int page,
                                             @RestQuery @DefaultValue("10") int size,
                                             ServicioQueryDto servicioQueryDto) {
        return servicioLaboratorioService.obtenerPageServicioLaboratorio(page, size, servicioQueryDto);
    }

    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public ServicioLaboratorio show(@RestPath Long id) {
        return servicioLaboratorioService.obtenerServicioLaboratorioPorId(id);
    }

    @POST
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public ServicioLaboratorio store(@Valid CrearServicioLaboratorioDto servicioLaboratorioDto) {
        return servicioLaboratorioService.registrarServicio(servicioLaboratorioDto);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public ServicioLaboratorio update(Long id, @Valid CrearServicioLaboratorioDto servicioLaboratorioDto) {
        return servicioLaboratorioService.actualizarServicio(id, servicioLaboratorioDto);
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void enable(@RestPath Long id) {
        servicioLaboratorioService.activarServicio(id);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void destroy(@RestPath Long id) {
        servicioLaboratorioService.desactivarServicio(id);
    }
}

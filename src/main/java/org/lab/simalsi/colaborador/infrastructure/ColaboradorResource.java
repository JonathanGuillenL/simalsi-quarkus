package org.lab.simalsi.colaborador.infrastructure;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.colaborador.application.ColaboradorQueryDto;
import org.lab.simalsi.colaborador.application.ColaboradorService;
import org.lab.simalsi.colaborador.application.CrearColaboradorDto;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.common.SimalsiRoles;

@Path("/colaborador")
public class ColaboradorResource {

    @Inject
    ColaboradorService colaboradorService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN})
    public PageDto<Colaborador> obtenerColaboradores(@RestQuery @DefaultValue("0") int page,
                                                     @RestQuery @DefaultValue("10") int size,
                                                     ColaboradorQueryDto colaboradorQueryDto) {
        return colaboradorService.obtenerListaColaboradores(page, size, colaboradorQueryDto);
    }

    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN})
    public Colaborador show(@RestPath Long id) {
        return colaboradorService.obtenerColaboradorPorId(id);
    }

    @POST
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public Colaborador store(@Valid CrearColaboradorDto colaboradorDto) {
        return colaboradorService.registrarColaborador(colaboradorDto);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public Colaborador update(@RestPath Long id, @Valid CrearColaboradorDto colaboradorDto) {
        return colaboradorService.actualizarColaborador(id, colaboradorDto);
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void enable(@RestPath Long id) {
        colaboradorService.activarColaborador(id);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void destroy(@RestPath Long id) {
        colaboradorService.desactivarColaborador(id);
    }
}

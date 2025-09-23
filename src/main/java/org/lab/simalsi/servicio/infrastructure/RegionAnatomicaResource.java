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
import org.lab.simalsi.servicio.application.CrearRegionAnatomicaDto;
import org.lab.simalsi.servicio.application.RegionAnatomicaQueryDto;
import org.lab.simalsi.servicio.application.RegionAnatomicaService;
import org.lab.simalsi.servicio.models.RegionAnatomica;

import java.util.List;

@Path("/region-anatomica")
public class RegionAnatomicaResource {

    @Inject
    private RegionAnatomicaService regionAnatomicaService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.HISTOTECNOLOGO, SimalsiRoles.PATOLOGO})
    public PageDto<RegionAnatomica> page(@RestQuery @DefaultValue("0") int page,
                                         @RestQuery @DefaultValue("10") int size,
                                         RegionAnatomicaQueryDto regionAnatomicaQueryDto) {
        return regionAnatomicaService.obtenerPageRegionAnatomica(page, size, regionAnatomicaQueryDto);
    }

    @GET
    @Path("list")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA, SimalsiRoles.HISTOTECNOLOGO, SimalsiRoles.PATOLOGO})
    public List<RegionAnatomica> list() {
        return regionAnatomicaService.obtenerListaRegionesAnatomicas();
    }

    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.HISTOTECNOLOGO, SimalsiRoles.PATOLOGO})
    public RegionAnatomica show(@RestPath Long id) {
        return regionAnatomicaService.obtenerRegionAnatomicaPorId(id);
    }

    @POST
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public RegionAnatomica store(@Valid CrearRegionAnatomicaDto regionAnatomicaDto) {
        return regionAnatomicaService.registrarRegionAnatomica(regionAnatomicaDto);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public RegionAnatomica update(@RestPath Long id, @Valid CrearRegionAnatomicaDto regionAnatomicaDto) {
        return regionAnatomicaService.actualizarRegionAnatomica(id, regionAnatomicaDto);
    }
}

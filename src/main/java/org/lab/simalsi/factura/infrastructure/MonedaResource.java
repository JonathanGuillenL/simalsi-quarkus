package org.lab.simalsi.factura.infrastructure;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.factura.application.CrearMonedaDto;
import org.lab.simalsi.factura.application.MonedaQueryDto;
import org.lab.simalsi.factura.application.MonedaService;
import org.lab.simalsi.factura.models.Moneda;

import java.util.List;

@Path("/moneda")
public class MonedaResource {

    @Inject
    private MonedaService monedaService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PageDto<Moneda> all(@RestQuery @DefaultValue("0") int page,
                               @RestQuery @DefaultValue("10") int size,
                               MonedaQueryDto monedaQueryDto) {
        return monedaService.obtenerPageMonedas(page, size, monedaQueryDto);
    }

    @GET
    @Path("list")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public List<Moneda> list() {
        return monedaService.obtenerListMonedas();
    }

    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Moneda show(@RestPath Long id) {
        return monedaService.obtenerMonedaPorId(id);
    }

    @POST
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Moneda store(@Valid CrearMonedaDto monedaDto) {
        return monedaService.registrarMoneda(monedaDto);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public Moneda update(@RestPath Long id, @Valid CrearMonedaDto monedaDto) {
        return monedaService.actualizarMoneda(id, monedaDto);
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void enable(@RestPath Long id) {
        monedaService.activarMoneda(id);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void destroy(@RestPath Long id) {
        monedaService.desactivarMoneda(id);
    }
}

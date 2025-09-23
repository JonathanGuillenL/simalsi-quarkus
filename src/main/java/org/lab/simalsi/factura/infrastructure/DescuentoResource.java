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
import org.lab.simalsi.factura.application.CrearDescuentoDto;
import org.lab.simalsi.factura.application.DescuentoQueryDto;
import org.lab.simalsi.factura.application.DescuentoService;
import org.lab.simalsi.factura.models.Descuento;

@Path("/descuento")
public class DescuentoResource {

    @Inject
    private DescuentoService descuentoService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PageDto<Descuento> page(@RestQuery @DefaultValue("0") int page,
                                   @RestQuery @DefaultValue("10") int size,
                                   DescuentoQueryDto descuentoQueryDto) {
        return descuentoService.obtenerPaginaDescuentos(page, size, descuentoQueryDto);
    }

    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Descuento show(@RestPath Long id) {
        return descuentoService.obtenerDescuentoPorId(id);
    }

    @POST
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public Descuento store(@Valid CrearDescuentoDto descuentoDto) {
        return descuentoService.registrarDescuento(descuentoDto);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public Descuento update(@RestPath Long id, @Valid CrearDescuentoDto descuentoDto) {
        return descuentoService.actualizarDescuento(id, descuentoDto);
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void enable(@RestPath Long id) {
        descuentoService.activarDescuento(id);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void destroy(@RestPath Long id) {
        descuentoService.desactivarDescuento(id);
    }
}

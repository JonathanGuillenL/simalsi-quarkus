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
import org.lab.simalsi.factura.application.CrearMetodoPagoDto;
import org.lab.simalsi.factura.application.MetodoPagoQueryDto;
import org.lab.simalsi.factura.application.MetodoPagoService;
import org.lab.simalsi.factura.models.MetodoPago;

import java.util.List;

@Path("/metodo-pago")
public class MetodoPagoResource {

    @Inject
    private MetodoPagoService metodoPagoService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PageDto<MetodoPago> all(@RestQuery @DefaultValue("0") int page,
                                   @RestQuery @DefaultValue("10") int size,
                                   MetodoPagoQueryDto metodoPagoQueryDto) {
        return metodoPagoService.obtenerPageMetodoPago(page, size, metodoPagoQueryDto);
    }

    @GET
    @Path("list")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public List<MetodoPago> list() {
        return metodoPagoService.obtenerListMetodoPago();
    }

    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public MetodoPago show(@RestPath Long id) {
        return metodoPagoService.obtenerMetodoPagoPorId(id);
    }

    @POST
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public MetodoPago store(@Valid CrearMetodoPagoDto metodoPagoDto) {
        return metodoPagoService.registrarMetodoPago(metodoPagoDto);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public MetodoPago update(@RestPath Long id, @Valid CrearMetodoPagoDto metodoPagoDto) {
        return metodoPagoService.actualizarMetodoPago(id, metodoPagoDto);
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void enable(@RestPath Long id) {
        metodoPagoService.activarMetodoPago(id);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void destroy(@RestPath Long id) {
        metodoPagoService.desactivarMetodoPago(id);
    }
}

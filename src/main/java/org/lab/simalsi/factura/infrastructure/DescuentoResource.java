package org.lab.simalsi.factura.infrastructure;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.application.CrearDescuentoDto;
import org.lab.simalsi.factura.application.DescuentoService;
import org.lab.simalsi.factura.models.Descuento;

@Path("/descuento")
public class DescuentoResource {

    @Inject
    private DescuentoService descuentoService;

    @GET
    public PageDto<Descuento> page(@RestQuery @DefaultValue("0") int page,
                                   @RestQuery @DefaultValue("10") int size) {
        return descuentoService.obtenerPaginaDescuentos(page, size);
    }

    @GET
    @Path("{id}")
    public Descuento show(@RestPath Long id) {
        return descuentoService.obtenerDescuentoPorId(id);
    }

    @POST
    public Descuento store(CrearDescuentoDto descuentoDto) {
        return descuentoService.registrarDescuento(descuentoDto);
    }
}

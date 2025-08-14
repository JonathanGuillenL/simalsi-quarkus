package org.lab.simalsi.factura.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.application.CrearMetodoPagoDto;
import org.lab.simalsi.factura.application.MetodoPagoService;
import org.lab.simalsi.factura.models.MetodoPago;

import java.util.List;

@Path("/metodo-pago")
public class MetodoPagoResource {

    @Inject
    private MetodoPagoService metodoPagoService;

    @GET
    public PageDto<MetodoPago> all(@RestQuery @DefaultValue("0") int page,
                                   @RestQuery @DefaultValue("10") int size) {
        return metodoPagoService.obtenerPageMetodoPago(page, size);
    }

    @GET
    @Path("list")
    public List<MetodoPago> list() {
        return metodoPagoService.obtenerListMetodoPago();
    }

    @GET
    @Path("{id}")
    public MetodoPago show(@RestPath Long id) {
        return metodoPagoService.obtenerMetodoPagoPorId(id);
    }

    @POST
    @Transactional
    public MetodoPago store(CrearMetodoPagoDto metodoPagoDto) {
        return metodoPagoService.registrarMetodoPago(metodoPagoDto);
    }
}

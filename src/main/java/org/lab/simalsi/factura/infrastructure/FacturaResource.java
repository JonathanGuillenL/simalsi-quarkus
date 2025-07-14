package org.lab.simalsi.factura.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.application.CrearFacturaDto;
import org.lab.simalsi.factura.application.FacturaService;
import org.lab.simalsi.factura.models.Factura;

@Path("/factura")
public class FacturaResource {

    @Inject
    FacturaService facturaService;

    @GET
    public PageDto<Factura> obtenerFacturas(@RestQuery @DefaultValue("0") int page,
                                            @RestQuery @DefaultValue("10") int size) {
        return facturaService.listarFacturas(page, size);
    }

    @POST
    @Transactional
    public Factura store(CrearFacturaDto facturaDto) {
        return facturaService.registrarFactura(facturaDto);
    }
}

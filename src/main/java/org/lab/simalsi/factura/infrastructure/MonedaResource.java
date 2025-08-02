package org.lab.simalsi.factura.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.application.CrearMonedaDto;
import org.lab.simalsi.factura.application.MonedaService;
import org.lab.simalsi.factura.models.Moneda;

import java.util.List;

@Path("/moneda")
public class MonedaResource {

    @Inject
    private MonedaService monedaService;

    @GET
    public PageDto<Moneda> all(@RestQuery @DefaultValue("0") int page,
                               @RestQuery @DefaultValue("10") int size) {
        return monedaService.obtenerPageMonedas(page, size);
    }

    @GET
    @Path("list")
    public List<Moneda> list() {
        return monedaService.obtenerListMonedas();
    }

    @POST
    @Transactional
    public Moneda store(CrearMonedaDto monedaDto) {
        return monedaService.registrarMoneda(monedaDto);
    }
}

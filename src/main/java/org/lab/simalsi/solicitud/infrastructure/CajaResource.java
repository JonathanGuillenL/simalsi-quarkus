package org.lab.simalsi.solicitud.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.solicitud.application.CajaService;
import org.lab.simalsi.solicitud.application.CrearCajaDto;
import org.lab.simalsi.solicitud.models.Caja;

import java.util.List;

@Path("/caja")
public class CajaResource {

    @Inject
    private CajaService cajaService;

    @GET
    public PageDto<Caja> page(@RestQuery @DefaultValue("0") int page,
                              @RestQuery @DefaultValue("10") int size) {
        return cajaService.obtenerPageCaja(page, size);
    }

    @GET
    @Path("list")
    public List<Caja> list() {
        return cajaService.obtenerListCajas();
    }

    @POST
    @Transactional
    public Caja store(CrearCajaDto cajaDto) {
        return cajaService.registrarCaja(cajaDto);
    }
}

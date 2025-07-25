package org.lab.simalsi.solicitud.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestPath;
import org.lab.simalsi.solicitud.application.CrearResultadoCGODto;
import org.lab.simalsi.solicitud.application.ResultadoCGOService;
import org.lab.simalsi.solicitud.models.ResultadoCGO;

@Path("/resultado/cgo")
public class ResultadoCGOResource {

    @Inject
    ResultadoCGOService resultadoCGOService;

    @POST
    @Path("/{solicitudId}")
    @Transactional
    public ResultadoCGO store(@RestPath Long solicitudId, CrearResultadoCGODto resultadoCGODto) {
        return resultadoCGOService.crearResultadoCGO(solicitudId, resultadoCGODto);
    }
}

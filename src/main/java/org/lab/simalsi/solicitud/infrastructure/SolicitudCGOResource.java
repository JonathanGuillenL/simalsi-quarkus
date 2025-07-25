package org.lab.simalsi.solicitud.infrastructure;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.lab.simalsi.solicitud.application.CrearSolicitudCGODto;
import org.lab.simalsi.solicitud.application.SolicitudService;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

@Path("/solicitud/cgo")
public class SolicitudCGOResource {

    @Inject
    SolicitudService solicitudService;

    @POST
    public SolicitudCGO store(CrearSolicitudCGODto solicitudCGODto) {
        return solicitudService.registrarSolicitudCGO(solicitudCGODto);
    }
}

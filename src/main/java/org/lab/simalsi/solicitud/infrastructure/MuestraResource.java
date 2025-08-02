package org.lab.simalsi.solicitud.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestPath;
import org.lab.simalsi.solicitud.application.AgregarMuestraDto;
import org.lab.simalsi.solicitud.application.MuestraService;
import org.lab.simalsi.solicitud.models.Muestra;

@Path("/muestra")
public class MuestraResource {

    @Inject
    MuestraService muestraService;

    @POST
    @Path("{solicitudId}")
    @Transactional
    public Muestra store(@RestPath Long solicitudId, AgregarMuestraDto muestraDto) {
        return muestraService.agregarMuestraASolicitud(solicitudId, muestraDto);
    }
}

package org.lab.simalsi.solicitud.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestPath;
import org.lab.simalsi.solicitud.application.AgregarLaminaDto;
import org.lab.simalsi.solicitud.application.AgregarMuestraDto;
import org.lab.simalsi.solicitud.application.MuestraService;
import org.lab.simalsi.solicitud.models.Lamina;
import org.lab.simalsi.solicitud.models.Muestra;

@Path("/muestra")
public class MuestraResource {

    @Inject
    MuestraService muestraService;

    @POST
    @Transactional
    public Muestra store(AgregarMuestraDto muestraDto) {
        return muestraService.agregarMuestraASolicitud(muestraDto);
    }

    @POST
    @Path("{id}/lamina")
    @Transactional
    public Lamina storeLamina(@RestPath Long id, AgregarLaminaDto laminaDto) {
        return muestraService.agregarLaminaAMuestra(id, laminaDto);
    }
}

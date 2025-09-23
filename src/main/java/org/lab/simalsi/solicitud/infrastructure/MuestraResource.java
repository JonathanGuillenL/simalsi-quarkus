package org.lab.simalsi.solicitud.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.resteasy.reactive.RestPath;
import org.lab.simalsi.solicitud.application.*;
import org.lab.simalsi.solicitud.models.Lamina;
import org.lab.simalsi.solicitud.models.Muestra;

@Path("/muestra")
public class MuestraResource {

    @Inject
    MuestraService muestraService;

    @Inject
    private SecurityContext securityContext;

    @POST
    @Path("{solicitudId}")
    @Transactional
    public MuestraResponseDto store(@RestPath Long solicitudId, @Valid AgregarMuestraDto muestraDto) {
        String userId = securityContext.getUserPrincipal() != null ?
            securityContext.getUserPrincipal().getName() : null;

        return muestraService.agregarMuestraASolicitud(solicitudId, userId, muestraDto);
    }

    @PUT
    @Path("{solicitudId}")
    @Transactional
    public MuestraResponseDto update(@RestPath Long solicitudId, @Valid AgregarMuestraDto muestraDto) {
        String userId = securityContext.getUserPrincipal() != null ?
            securityContext.getUserPrincipal().getName() : null;

        return muestraService.actualizarMuestraSolicitud(solicitudId, userId, muestraDto);
    }

    @POST
    @Path("mover/{laminaId}")
    @Transactional
    public LaminaResponseDto moverLamina(@RestPath Long laminaId, MoverLaminaDto moverLaminaDto) {
        return muestraService.moverLamina(laminaId, moverLaminaDto);
    }
}

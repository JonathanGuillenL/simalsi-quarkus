package org.lab.simalsi.solicitud.infrastructure;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.solicitud.application.CodigoEnfermedadesService;
import org.lab.simalsi.solicitud.models.CodigoEnfermedades;

import java.util.List;

@Path("/codigo-enfermedades")
public class CodigoEnfermedadesResource {

    @Inject
    private CodigoEnfermedadesService codigoEnfermedadesService;

    @GET
    @Path("list")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.HISTOTECNOLOGO, SimalsiRoles.PATOLOGO})
    public List<CodigoEnfermedades> list() {
        return codigoEnfermedadesService.obtenerListaCodigoEnfermedades();
    }
}

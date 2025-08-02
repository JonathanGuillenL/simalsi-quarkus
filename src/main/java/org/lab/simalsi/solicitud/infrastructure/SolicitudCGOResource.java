package org.lab.simalsi.solicitud.infrastructure;

import io.quarkus.logging.Log;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.solicitud.application.CrearSolicitudCGODto;
import org.lab.simalsi.solicitud.application.SolicitudService;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

import java.net.URI;
import java.time.temporal.ChronoUnit;

@Path("/solicitud/cgo")
@RolesAllowed("ROLE_RECEPCIONISTA")
public class SolicitudCGOResource {

    @Inject
    SecurityContext securityContext;

    @Inject
    JWTParser jwtParser;

    @Inject
    SolicitudService solicitudService;

    @GET
    public PageDto<DetalleFactura> all(@RestQuery @DefaultValue("0") int page,
                                       @RestQuery @DefaultValue("10") int size) {
        return solicitudService.obtenerListaSolicitudes(page, size);
    }

    @GET
    @Path("no-facturada/{clienteId}")
    public PageDto<SolicitudCGO> noFacturadas(@RestQuery @DefaultValue("0") int page,
                                       @RestQuery @DefaultValue("10") int size,
                                       @RestPath Long clienteId) {
        return solicitudService.obtenerListaSolicitudesNoFacturadasPorClienteId(page, size, clienteId);
    }

    @GET
    @Path("{id}")
    public SolicitudCGO show(@RestPath Long id) {
        return solicitudService.obtenerSolicitudPorId(id);
    }

    @POST
    @Transactional
    public SolicitudCGO store(CrearSolicitudCGODto solicitudCGODto) {
        String userId = securityContext.getUserPrincipal() != null
            ? securityContext.getUserPrincipal().getName()
            : null;
        return solicitudService.registrarSolicitudCGO(userId, solicitudCGODto);
    }

    @POST
    @Path("generate-token/{id}")
    @RolesAllowed("ROLE_RECEPCIONISTA")
    public Response generateToken(@RestPath Long id, @Context UriInfo info) {
        String userId = securityContext.getUserPrincipal() != null
            ? securityContext.getUserPrincipal().getName()
            : null;

        String token = solicitudService.generateToken(id, userId, 5, ChronoUnit.MINUTES);

        URI location = info.getBaseUriBuilder()
            .path("solicitud")
            .path("cgo")
            .path("print")
            .path(id.toString())
            .queryParam("token", token)
            .build();
        return Response.created(location).build();
    }

    @GET
    @Path("/print/{id}")
    @PermitAll
    @Produces("application/pdf")
    public Response print(@RestPath Long id, @RestQuery String token) {
        JsonWebToken jwt = null;
        try {
            jwt = jwtParser.parse(token);

            String facturaId = jwt.getClaim("solicitud_id").toString();
            String userId = jwt.getSubject();

            Long f = Long.valueOf(facturaId);

            Log.info("solicitud id obtenida " + facturaId);

            byte[] pdf = solicitudService.generarSolicitudPdf(f);

            return Response
                .ok(pdf, "application/pdf")
                .header("Content-Disposition", "inline; filename=\"Report.pdf\"")
                .build();
        } catch (ParseException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}

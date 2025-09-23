package org.lab.simalsi.solicitud.infrastructure;

import io.quarkus.logging.Log;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.solicitud.application.*;
import org.lab.simalsi.solicitud.models.SolicitudCGO;
import org.lab.simalsi.solicitud.models.SolicitudEstado;

import java.net.URI;
import java.time.temporal.ChronoUnit;

@Path("/solicitud/cgo")
public class SolicitudCGOResource {

    @Inject
    SecurityContext securityContext;

    @Inject
    JWTParser jwtParser;

    @Inject
    SolicitudService solicitudService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA, SimalsiRoles.HISTOTECNOLOGO, SimalsiRoles.PATOLOGO})
    public PageDto<SolicitudPageResponse> all(@RestQuery @DefaultValue("0") int page,
                                              @RestQuery @DefaultValue("10") int size,
                                              SolicitudCGOQueryDto solicitudCGOQueryDto) {
        if (securityContext.isUserInRole(SimalsiRoles.RECEPCIONISTA) || securityContext.isUserInRole(SimalsiRoles.ADMIN)) {
            return solicitudService.obtenerListaSolicitudes(page, size, solicitudCGOQueryDto);
        } else if (securityContext.isUserInRole(SimalsiRoles.HISTOTECNOLOGO)) {
            solicitudCGOQueryDto.solicitudEstado = SolicitudEstado.FACTURADO;
            return solicitudService.obtenerListaSolicitudes(page, size, solicitudCGOQueryDto);
        } else if (securityContext.isUserInRole(SimalsiRoles.PATOLOGO)) {
            solicitudCGOQueryDto.solicitudEstado = SolicitudEstado.PROCESADO;
            return solicitudService.obtenerListaSolicitudes(page, size, solicitudCGOQueryDto);
        }

        return null;
    }

    @GET
    @Path("historial")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.CLIENTE, SimalsiRoles.HISTOTECNOLOGO, SimalsiRoles.PATOLOGO})
    public PageDto<SolicitudPageResponse> historial(@RestQuery @DefaultValue("0") int page,
                                     @RestQuery @DefaultValue("10") int size,
                                     SolicitudCGOQueryDto solicitudCGOQueryDto) {
        String userId = securityContext.getUserPrincipal() != null ?
            securityContext.getUserPrincipal().getName() : null;

        if (securityContext.isUserInRole("ROLE_HISTOTECNOLOGO")) {
            return solicitudService.obtenerHistorialSolicitudes(page, size, userId, "ROLE_HISTOTECNOLOGO", solicitudCGOQueryDto);
        } else if (securityContext.isUserInRole("ROLE_PATOLOGO")) {
            return solicitudService.obtenerHistorialSolicitudes(page, size, userId, "ROLE_PATOLOGO", solicitudCGOQueryDto);
        } else if (securityContext.isUserInRole("ROLE_CLIENTE")) {
            return solicitudService.obtenerHistorialSolicitudes(page, size, userId, "ROLE_CLIENTE", solicitudCGOQueryDto);
        }

        return null;
    }

    @GET
    @Path("no-facturada/{clienteId}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PageDto<SolicitudPageResponse> noFacturadas(@RestQuery @DefaultValue("0") int page,
                                       @RestQuery @DefaultValue("10") int size,
                                       @RestPath Long clienteId) {
        return solicitudService.obtenerListaSolicitudesNoFacturadasPorClienteId(page, size, clienteId);
    }

    @GET
    @Path("{id}")
    public SolicitudCGOResponse show(@RestPath Long id) {
        return solicitudService.obtenerSolicitudPorId(id);
    }

    @POST
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public SolicitudCGOResponse store(@Valid CrearSolicitudCGODto solicitudCGODto) {
        String userId = securityContext.getUserPrincipal() != null
            ? securityContext.getUserPrincipal().getName()
            : null;
        return solicitudService.registrarSolicitudCGO(userId, solicitudCGODto);
    }

    @POST
    @Path("generate-token/{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.CLIENTE, SimalsiRoles.RECEPCIONISTA, SimalsiRoles.HISTOTECNOLOGO, SimalsiRoles.PATOLOGO})
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

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void destroy(@RestPath Long id) {
        solicitudService.desactivarSolicitud(id);
    }
}

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
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.solicitud.application.*;
import org.lab.simalsi.solicitud.models.ArchivoAdjunto;
import org.lab.simalsi.solicitud.models.ResultadoCGO;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.net.URI;
import java.time.temporal.ChronoUnit;

@Path("/resultado/cgo")
public class ResultadoCGOResource {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private JWTParser jwtParser;

    @Inject
    ResultadoCGOService resultadoCGOService;

    @Inject
    SolicitudService solicitudService;

    @POST
    @Path("/{solicitudId}")
    @Transactional
    public ResultadoCGO store(@RestPath Long solicitudId, @Valid CrearResultadoCGODto resultadoCGODto) {
        String userId = securityContext.getUserPrincipal() != null ?
            securityContext.getUserPrincipal().getName() : null;
        return resultadoCGOService.crearResultadoCGO(solicitudId, userId, resultadoCGODto);
    }

    @POST
    @Path("/upload/{resultadoId}")
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@RestPath Long resultadoId, FileFormData formData) throws Exception {
        ArchivoAdjunto archivoAdjunto = resultadoCGOService.asociarArchivo(resultadoId, formData);

        return Response.ok(archivoAdjunto).build();
    }

    @GET
    @Path("/download/{objectkey}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response descargarArchivo(@RestPath String objectkey) {
        ResponseBytes<GetObjectResponse> objectBytes = resultadoCGOService.obtenerArchivoPorNombre(objectkey);
        Response.ResponseBuilder response = Response.ok(objectBytes.asByteArray());
        response.header("Content-Disposition", "inline;filename=" + objectkey);
        response.header("Content-Type", objectBytes.response().contentType());
        return response.build();
    }

    @DELETE
    @Path("/delete/{archivoId}")
    @Transactional
    public Response destroyArchivo(@RestPath Long archivoId) throws Exception {
        ArchivoAdjunto archivoAdjunto = resultadoCGOService.deshabilitarArchivo(archivoId);

        return Response.ok(archivoAdjunto).build();
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
            .path("resultado")
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

            byte[] pdf = resultadoCGOService.generarResultadoPdf(f);

            return Response
                .ok(pdf, "application/pdf")
                .header("Content-Disposition", "inline; filename=\"Report.pdf\"")
                .build();
        } catch (ParseException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/print")
    @PermitAll
    @Produces("application/pdf")
    public Response print(@RestQuery String username, @RestQuery String ticket) {
        byte[] pdf = resultadoCGOService.generarResultadoPdfByTicket(username, ticket);

        if (pdf == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .build();
        }

        return Response
            .ok(pdf, "application/pdf")
            .header("Content-Disposition", "inline; filename=\"Report.pdf\"")
            .build();
    }
}

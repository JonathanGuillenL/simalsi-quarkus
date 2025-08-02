package org.lab.simalsi.factura.infrastructure;

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
import org.lab.simalsi.factura.application.CrearFacturaDto;
import org.lab.simalsi.factura.application.FacturaService;
import org.lab.simalsi.factura.application.PagoDto;
import org.lab.simalsi.factura.application.PagoService;
import org.lab.simalsi.factura.models.Factura;
import org.lab.simalsi.factura.models.Pago;

import java.net.URI;
import java.time.temporal.ChronoUnit;

@Path("/factura")
public class FacturaResource {

    @Inject
    SecurityContext securityContext;

    @Inject
    FacturaService facturaService;

    @Inject
    private PagoService pagoService;

    @Inject
    JWTParser jwtParser;

    @GET
    @RolesAllowed("ROLE_RECEPCIONISTA")
    public PageDto<Factura> obtenerFacturas(@RestQuery @DefaultValue("0") int page,
                                            @RestQuery @DefaultValue("10") int size) {
        return facturaService.listarFacturas(page, size);
    }

    @GET
    @Path("{id}")
    public Factura show(@RestPath Long id) {
        return facturaService.obtenerFacturaPorId(id);
    }

    @POST
    @Path("generate-token/{id}")
    @RolesAllowed("ROLE_RECEPCIONISTA")
    public Response generateToken(@RestPath Long id, @Context UriInfo info) {
        String userId = securityContext.getUserPrincipal() != null
            ? securityContext.getUserPrincipal().getName()
            : null;

        String token = facturaService.generateToken(id, userId, 5, ChronoUnit.MINUTES);

        URI location = info.getBaseUriBuilder()
            .path("factura")
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

            String facturaId = jwt.getClaim("factura_id").toString();
            String userId = jwt.getSubject();

            Long f = Long.valueOf(facturaId);

            Log.info("factura id obtenida " + facturaId);

            byte[] pdf = facturaService.generarFacturaPdf(f);

            return Response
                .ok(pdf, "application/pdf")
                .header("Content-Disposition", "inline; filename=\"Report.pdf\"")
                .build();
        } catch (ParseException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Transactional
    @RolesAllowed("ROLE_RECEPCIONISTA")
    public Factura store(@Valid CrearFacturaDto facturaDto) {
        String userId = securityContext.getUserPrincipal() != null
            ? securityContext.getUserPrincipal().getName() : null;
        return facturaService.registrarFactura(userId, facturaDto);
    }

    @POST
    @Transactional
    @Path("/pago/{id}")
    public Pago pago(@RestPath Long id, @Valid PagoDto pagoDto) {
        return pagoService.realizarPago(id, pagoDto);
    }
}

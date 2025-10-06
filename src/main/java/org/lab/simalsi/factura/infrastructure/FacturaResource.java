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
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.factura.application.*;
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
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.CLIENTE, SimalsiRoles.RECEPCIONISTA})
    public PageDto<FacturaPageResponseDto> obtenerFacturas(@RestQuery @DefaultValue("0") int page,
                                            @RestQuery @DefaultValue("10") int size,
                                            FacturaQueryDto facturaQueryDto) {
        if (securityContext.getUserPrincipal() != null && securityContext.isUserInRole(SimalsiRoles.CLIENTE)) {
            return facturaService.listarFacturasByCliente(page, size, securityContext.getUserPrincipal().getName(), facturaQueryDto);
        }
        return facturaService.listarFacturas(page, size, facturaQueryDto);
    }

    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.CLIENTE, SimalsiRoles.RECEPCIONISTA})
    public FacturaResponseDto show(@RestPath Long id) {
        return facturaService.obtenerFacturaPorId(id);
    }

    @POST
    @Path("generate-token/{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.CLIENTE, SimalsiRoles.RECEPCIONISTA})
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
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Factura store(@Valid CrearFacturaDto facturaDto) {
        String userId = securityContext.getUserPrincipal() != null
            ? securityContext.getUserPrincipal().getName() : null;
        return facturaService.registrarFactura(userId, facturaDto);
    }

    @POST
    @Transactional
    @Path("/{facturaId}/pago")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Factura pago(@RestPath Long facturaId, @Valid PagoDto pagoDto) {
        return pagoService.realizarPago(facturaId, pagoDto);
    }

    @PUT
    @Transactional
    @Path("/{facturaId}/pago/{pagoId}")
    @RolesAllowed({SimalsiRoles.ADMIN})
    public FacturaResponseDto anularPago(@RestPath Long facturaId, @RestPath Long pagoId) {
        return pagoService.anularPago(facturaId, pagoId);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void destroy(@RestPath Long id) {
        facturaService.deshabilitarFactura(id);
    }
}

package org.lab.simalsi.servicio.infrastructure;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.servicio.application.CrearProcedimientoQuirurgicoDto;
import org.lab.simalsi.servicio.application.ProcedimientoQuirurgicoQueryDto;
import org.lab.simalsi.servicio.application.ProcedimientoQuirurgicoService;
import org.lab.simalsi.servicio.models.ProcedimientoQuirurgico;
import org.lab.simalsi.servicio.models.RegionAnatomica;

import java.util.List;

@Path("/procedimiento-quirurgico")
public class ProcedimientoQuirurgicoResource {

    @Inject
    private ProcedimientoQuirurgicoService procedimientoQuirurgicoService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.HISTOTECNOLOGO, SimalsiRoles.PATOLOGO})
    public PageDto<ProcedimientoQuirurgico> page(@RestQuery @DefaultValue("0") int page,
                                                 @RestQuery @DefaultValue("10") int size,
                                                 ProcedimientoQuirurgicoQueryDto procedimientoQuirurgicoQueryDto) {
        return procedimientoQuirurgicoService.obtenerPageProcedimientoQuirurgico(page, size, procedimientoQuirurgicoQueryDto);
    }

    @GET
    @Path("region-anatomica/{regionId}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA, SimalsiRoles.HISTOTECNOLOGO, SimalsiRoles.PATOLOGO})
    public List<ProcedimientoQuirurgico> listByRegionId(@RestPath Long regionId) {
        return procedimientoQuirurgicoService.obtenerListaProcedimientoPorRegionId(regionId);
    }

    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.HISTOTECNOLOGO, SimalsiRoles.PATOLOGO})
    public ProcedimientoQuirurgico show(@RestPath Long id) {
        return procedimientoQuirurgicoService.obtenerProcedimientoQuirurgicoPorId(id);
    }

    @POST
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public ProcedimientoQuirurgico store(@Valid CrearProcedimientoQuirurgicoDto procedimientoQuirurgicoDto) {
        return procedimientoQuirurgicoService.registrarProcedimientoQuirurgico(procedimientoQuirurgicoDto);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public ProcedimientoQuirurgico update(@RestPath Long id, @Valid CrearProcedimientoQuirurgicoDto procedimientoQuirurgicoDto) {
        return procedimientoQuirurgicoService.actualizarProcedimientoQuirurgico(id, procedimientoQuirurgicoDto);
    }
}

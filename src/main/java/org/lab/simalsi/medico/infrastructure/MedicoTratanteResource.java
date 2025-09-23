package org.lab.simalsi.medico.infrastructure;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.medico.application.*;
import org.lab.simalsi.medico.models.MedicoTratante;

@Path("/medico-tratante")
public class MedicoTratanteResource {

    @Inject
    private MedicoTratanteService medicoTratanteService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PageDto<MedicoTratanteResponsePageDto> page(@RestQuery @DefaultValue("0") int page,
                                                       @RestQuery @DefaultValue("10") int size,
                                                       MedicoTratanteQueryDto medicoTratanteQueryDto) {
        return medicoTratanteService.obtenerListaMedicosTrantantes(page, size, medicoTratanteQueryDto);
    }

    @GET
    @Path("no-cliente")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PageDto<MedicoTratanteResponsePageDto> noCliente(@RestQuery @DefaultValue("0") int page,
                                                       @RestQuery @DefaultValue("10") int size,
                                                       MedicoTratanteQueryDto medicoTratanteQueryDto) {
        return medicoTratanteService.obtenerListaMedicosTrantantesWithoutCliente(page, size, medicoTratanteQueryDto);
    }

    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public MedicoTratanteResponseDto show(@RestPath Long id) {
        return medicoTratanteService.obtenerMedicoPorId(id);
    }

    @GET
    @Path("persona/{personaId}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public MedicoTratanteResponseDto showByPersonaId(@RestPath Long personaId) {
        return medicoTratanteService.obtenerMedicoPorPersonaId(personaId);
    }

    @POST
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public MedicoTratante store(@Valid CrearMedicoTratante medicoTratanteDto) {
        return medicoTratanteService.registrarMedicoTratante(medicoTratanteDto);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public MedicoTratante store(@RestPath Long id, @Valid CrearMedicoTratante medicoTratanteDto) {
        return medicoTratanteService.actualizarMedicoTratante(id, medicoTratanteDto);
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void enable(@RestPath Long id) {
        medicoTratanteService.activarMedicoTratante(id);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void destroy(@RestPath Long id) {
        medicoTratanteService.desactivarMedicoTratanta(id);
    }
}

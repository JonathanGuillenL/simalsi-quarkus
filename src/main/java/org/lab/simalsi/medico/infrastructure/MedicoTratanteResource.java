package org.lab.simalsi.medico.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.medico.application.*;
import org.lab.simalsi.medico.models.MedicoTratante;

@Path("/medico-tratante")
public class MedicoTratanteResource {

    @Inject
    private MedicoTratanteService medicoTratanteService;

    @GET
    public PageDto<MedicoTratanteResponsePageDto> page(@RestQuery @DefaultValue("0") int page,
                                                       @RestQuery @DefaultValue("10") int size,
                                                       MedicoTratanteQueryDto medicoTratanteQueryDto) {
        return medicoTratanteService.obtenerListaMedicosTrantantes(page, size, medicoTratanteQueryDto);
    }

    @GET
    @Path("{id}")
    public MedicoTratanteResponseDto show(@RestPath Long id) {
        return medicoTratanteService.obtenerMedicoPorId(id);
    }

    @POST
    @Transactional
    public MedicoTratante store(@Valid CrearMedicoTratante medicoTratanteDto) {
        return medicoTratanteService.registrarMedicoTratante(medicoTratanteDto);
    }
}

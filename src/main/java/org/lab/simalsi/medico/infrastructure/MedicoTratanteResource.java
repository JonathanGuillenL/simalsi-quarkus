package org.lab.simalsi.medico.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.medico.application.CrearMedicoTratante;
import org.lab.simalsi.medico.application.MedicoTratanteService;
import org.lab.simalsi.medico.models.MedicoTratante;

@Path("/medico-tratante")
public class MedicoTratanteResource {

    @Inject
    private MedicoTratanteService medicoTratanteService;

    @GET
    public PageDto<MedicoTratante> page(@RestQuery @DefaultValue("0") int page,
                                        @RestQuery @DefaultValue("10") int size) {
        return medicoTratanteService.obtenerListaMedicosTrantantes(page, size);
    }

    @GET
    @Path("{id}")
    public MedicoTratante show(@RestPath Long id) {
        return medicoTratanteService.obtenerMedicoPorId(id);
    }

    @POST
    @Transactional
    public MedicoTratante store(CrearMedicoTratante medicoTratanteDto) {
        return medicoTratanteService.registrarMedicoTratante(medicoTratanteDto);
    }
}

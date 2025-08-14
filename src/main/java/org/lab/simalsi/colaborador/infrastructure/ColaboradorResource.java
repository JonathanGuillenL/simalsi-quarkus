package org.lab.simalsi.colaborador.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.colaborador.application.ColaboradorService;
import org.lab.simalsi.colaborador.application.CrearColaboradorDto;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.common.PageDto;

@Path("/colaborador")
public class ColaboradorResource {

    @Inject
    ColaboradorService colaboradorService;

    @GET
    public PageDto<Colaborador> obtenerColaboradores(@RestQuery @DefaultValue("0") int page,
                                                     @RestQuery @DefaultValue("10") int size) {
        return colaboradorService.obtenerListaColaboradores(page, size);
    }

    @POST
    @Transactional
    public Colaborador store(@Valid CrearColaboradorDto colaboradorDto) {
        return colaboradorService.registrarColaborador(colaboradorDto);
    }
}

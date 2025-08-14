package org.lab.simalsi.colaborador.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.colaborador.application.CargoService;
import org.lab.simalsi.colaborador.application.CrearCargoDto;
import org.lab.simalsi.colaborador.models.Cargo;
import org.lab.simalsi.common.PageDto;

import java.util.List;

@Path("/cargo")
public class CargoResource {

    @Inject
    private CargoService cargoService;

    @GET
    public PageDto<Cargo> page(@RestQuery @DefaultValue("0") int page,
                               @RestQuery @DefaultValue("10") int size) {
        return cargoService.obtenerPageCargo(page, size);
    }

    @GET
    @Path("/list")
    public List<Cargo> list() {
        return cargoService.obtenerListaCargo();
    }

    @POST
    @Transactional
    public Cargo store(CrearCargoDto cargoDto) {
        return cargoService.registrarCargo(cargoDto);
    }
}

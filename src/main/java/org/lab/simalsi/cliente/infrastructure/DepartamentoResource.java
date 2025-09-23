package org.lab.simalsi.cliente.infrastructure;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.lab.simalsi.cliente.application.DepartamentoService;
import org.lab.simalsi.cliente.models.Departamento;
import org.lab.simalsi.cliente.models.Municipio;

import java.util.List;

@Path("/departamento")
public class DepartamentoResource {

    @Inject
    private DepartamentoService departamentoService;

    @Inject
    private MunicipioRepository municipioRepository;

    @GET
    @Path("list")
    public List<Departamento> list() {
        return departamentoService.obtenerListaDepartamento();
    }

    @GET
    @Path("{id}/municipio/list")
    public List<Municipio> listMunicipio(Long id) {
        return municipioRepository.findByDepartamentoId(id);
    }
}

package org.lab.simalsi.cliente.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lab.simalsi.cliente.infrastructure.MunicipioRepository;
import org.lab.simalsi.cliente.models.Municipio;

import java.util.List;

@ApplicationScoped
public class MunicipioService {
    
    @Inject
    private MunicipioRepository municipioRepository;

    public List<Municipio> obtenerListaMunicipioPorDepartamentoId(Long departamentoId) {
        return municipioRepository.findByDepartamentoId(departamentoId);
    }
}

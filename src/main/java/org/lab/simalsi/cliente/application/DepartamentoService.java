package org.lab.simalsi.cliente.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lab.simalsi.cliente.infrastructure.DepartamentoRepository;
import org.lab.simalsi.cliente.models.Departamento;

import java.util.List;

@ApplicationScoped
public class DepartamentoService {

    @Inject
    private DepartamentoRepository departamentoRepository;

    public List<Departamento> obtenerListaDepartamento() {
        return departamentoRepository.listAll();
    }
}

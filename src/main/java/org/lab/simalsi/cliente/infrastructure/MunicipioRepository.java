package org.lab.simalsi.cliente.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.cliente.models.Municipio;

import java.util.List;

@ApplicationScoped
public class MunicipioRepository implements PanacheRepository<Municipio> {

    public List<Municipio> findByDepartamentoId(Long departamentoId) {
        return find("departamento.id", departamentoId).list();
    }
}

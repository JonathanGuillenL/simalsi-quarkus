package org.lab.simalsi.cliente.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.cliente.models.Departamento;

@ApplicationScoped
public class DepartamentoRepository implements PanacheRepository<Departamento> {
}

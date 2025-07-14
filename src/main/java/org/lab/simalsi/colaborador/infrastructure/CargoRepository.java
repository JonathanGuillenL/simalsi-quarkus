package org.lab.simalsi.colaborador.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.colaborador.models.Cargo;

@ApplicationScoped
public class CargoRepository implements PanacheRepository<Cargo> {
}

package org.lab.simalsi.colaborador.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.colaborador.models.Colaborador;

import java.util.Optional;

@ApplicationScoped
public class ColaboradorRepository implements PanacheRepository<Colaborador> {

    public Optional<Colaborador> findColaboradorByUsername(String username) {
        return find("username", username).stream().findFirst();
    }
}

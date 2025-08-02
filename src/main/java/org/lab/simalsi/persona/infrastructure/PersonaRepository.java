package org.lab.simalsi.persona.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.persona.models.Persona;

@ApplicationScoped
public class PersonaRepository implements PanacheRepository<Persona> {
}

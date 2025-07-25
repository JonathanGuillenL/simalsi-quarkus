package org.lab.simalsi.paciente.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.paciente.models.Paciente;

@ApplicationScoped
public class PacienteRepository implements PanacheRepository<Paciente> {
}

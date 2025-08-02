package org.lab.simalsi.medico.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.medico.models.MedicoTratante;

@ApplicationScoped
public class MedicoTratanteRepository implements PanacheRepository<MedicoTratante> {
}

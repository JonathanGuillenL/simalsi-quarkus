package org.lab.simalsi.servicio.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;

@ApplicationScoped
public class ServicioLaboratorioRepository implements PanacheRepository<ServicioLaboratorio> {
}

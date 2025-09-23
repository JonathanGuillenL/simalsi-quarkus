package org.lab.simalsi.solicitud.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.solicitud.models.CodigoEnfermedades;

@ApplicationScoped
public class CodigoEnfermedadesRepository implements PanacheRepository<CodigoEnfermedades> {
}

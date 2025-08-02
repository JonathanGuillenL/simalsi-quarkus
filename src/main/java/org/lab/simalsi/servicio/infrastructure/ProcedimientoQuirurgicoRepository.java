package org.lab.simalsi.servicio.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.servicio.models.ProcedimientoQuirurgico;

@ApplicationScoped
public class ProcedimientoQuirurgicoRepository implements PanacheRepository<ProcedimientoQuirurgico> {
}

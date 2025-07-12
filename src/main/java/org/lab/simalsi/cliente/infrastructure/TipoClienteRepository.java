package org.lab.simalsi.cliente.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.cliente.models.TipoCliente;

@ApplicationScoped
public class TipoClienteRepository implements PanacheRepository<TipoCliente> {
}

package org.lab.simalsi.cliente.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.cliente.models.Cliente;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
}

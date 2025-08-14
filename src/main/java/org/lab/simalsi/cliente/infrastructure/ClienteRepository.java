package org.lab.simalsi.cliente.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.cliente.application.ClienteQueryDto;
import org.lab.simalsi.cliente.models.Cliente;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public boolean existsByEmail(String email) {
        return find("email", email).firstResultOptional().isPresent();
    }

    public PanacheQuery<Cliente> findByQueryDto(ClienteQueryDto clienteQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (clienteQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", clienteQueryDto.id);
        }
        if (clienteQueryDto.nombres != null) {
            query.append(" and persona.nombre like :nombre or persona.apellido like :nombre");
            params.put("nombre", "%" + clienteQueryDto.nombres + "%");
        }
        if (clienteQueryDto.telefono != null) {
            query.append(" and persona.telefono = :telefono");
            params.put("telefono", clienteQueryDto.telefono);
        }
        if (clienteQueryDto.usuario != null) {
            query.append(" and username = :username");
            params.put("username", clienteQueryDto.usuario);
        }

        if (!params.isEmpty()) {
            return find(query.toString(), params);
        }

        return findAll();
    }
}

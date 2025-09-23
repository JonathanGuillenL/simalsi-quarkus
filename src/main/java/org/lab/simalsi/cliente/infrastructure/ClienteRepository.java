package org.lab.simalsi.cliente.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.cliente.application.ClienteQueryDto;
import org.lab.simalsi.cliente.models.Cliente;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Optional<Cliente> findByUsername(String username) {
        return find("username", username).stream().findFirst();
    }

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
        if (clienteQueryDto.email != null) {
            query.append(" and email like :email");
            params.put("email", "%" + clienteQueryDto.email + "%");
        }
        if (clienteQueryDto.tipoCliente != null) {
            query.append(" and tipoCliente = :tipoCliente");
            params.put("tipoCliente", clienteQueryDto.tipoCliente);
        }
        if (clienteQueryDto.estado != null) {
            String q = switch (clienteQueryDto.estado) {
                case INACTIVO -> " and deletedAt is not null";
                case ACTIVO -> " and deletedAt is null";
            };

            query.append(q);
        }

        if (!params.isEmpty()) {
            return find(query.toString(), Sort.ascending("id"), params);
        }

        return find(query.toString(), Sort.ascending("id"));
    }
}

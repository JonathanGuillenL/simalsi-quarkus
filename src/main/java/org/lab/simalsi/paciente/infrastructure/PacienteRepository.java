package org.lab.simalsi.paciente.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.common.ResourceState;
import org.lab.simalsi.paciente.application.PacienteQueryDto;
import org.lab.simalsi.paciente.models.Paciente;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class PacienteRepository implements PanacheRepository<Paciente> {
    public Paciente findByPersonaId(Long personaId) {
        return find("persona.id", personaId).firstResult();
    }

    public PanacheQuery<Paciente> findByQueryDto(PacienteQueryDto pacienteQueryDto) {
        return findByQueryDto(pacienteQueryDto, false);
    }

    public PanacheQuery<Paciente> findByQueryDto(PacienteQueryDto pacienteQueryDto, boolean onlyNoCliente) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (pacienteQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", pacienteQueryDto.id);
        }
        if (onlyNoCliente) {
            query.append(" and persona.cliente IS NULL");
        }
        if (pacienteQueryDto.nombres != null) {
            query.append(" and persona.nombre like :nombre or persona.apellido like :nombre");
            params.put("nombre", "%" + pacienteQueryDto.nombres + "%");
        }
        if (pacienteQueryDto.telefono != null) {
            query.append(" and persona.telefono = :telefono");
            params.put("telefono", pacienteQueryDto.telefono);
        }
        if (pacienteQueryDto.sexo != null) {
            query.append(" and sexo = :sexo");
            params.put("sexo", pacienteQueryDto.sexo);
        }
        if (pacienteQueryDto.estado != null) {
            String q = switch (pacienteQueryDto.estado) {
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

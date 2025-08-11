package org.lab.simalsi.paciente.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.paciente.application.PacienteQueryDto;
import org.lab.simalsi.paciente.models.Paciente;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PacienteRepository implements PanacheRepository<Paciente> {

    public PanacheQuery<Paciente> findByQueryDto(PacienteQueryDto pacienteQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (pacienteQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", pacienteQueryDto.id);
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

        if (!params.isEmpty()) {
            return find(query.toString(), params);
        }

        return findAll();
    }
}

package org.lab.simalsi.medico.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.medico.application.MedicoTratanteQueryDto;
import org.lab.simalsi.medico.models.MedicoTratante;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MedicoTratanteRepository implements PanacheRepository<MedicoTratante> {

    public boolean existsByCodigoSanitario(String codigoSanitario) {
        return find("codigoSanitario", codigoSanitario).firstResultOptional().isPresent();
    }

    public PanacheQuery<MedicoTratante> findByQueryDto(MedicoTratanteQueryDto medicoTratanteQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (medicoTratanteQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", medicoTratanteQueryDto.id);
        }
        if (medicoTratanteQueryDto.nombres != null) {
            query.append(" and persona.nombre like :nombre or persona.apellido like :nombre");
            params.put("nombre", "%" + medicoTratanteQueryDto.nombres + "%");
        }
        if (medicoTratanteQueryDto.telefono != null) {
            query.append(" and persona.telefono = :telefono");
            params.put("telefono", medicoTratanteQueryDto.telefono);
        }
        if (medicoTratanteQueryDto.codigoSanitario != null) {
            query.append(" and codigoSanitario = :codigoSanitario");
            params.put("codigoSanitario", medicoTratanteQueryDto.codigoSanitario);
        }

        if (!params.isEmpty()) {
            return find(query.toString(), params);
        }

        return findAll();
    }
}

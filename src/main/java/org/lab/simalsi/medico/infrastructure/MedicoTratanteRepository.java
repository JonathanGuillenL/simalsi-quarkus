package org.lab.simalsi.medico.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.medico.application.MedicoTratanteQueryDto;
import org.lab.simalsi.medico.models.MedicoTratante;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class MedicoTratanteRepository implements PanacheRepository<MedicoTratante> {

    public Optional<MedicoTratante> findByPersonaId(Long personaId) {
        return find("persona.id", personaId).firstResultOptional();
    }

    public Optional<MedicoTratante> existsByCodigoSanitario(String codigoSanitario) {
        return find("codigoSanitario", codigoSanitario).firstResultOptional();
    }

    public PanacheQuery<MedicoTratante> findByQueryDto(MedicoTratanteQueryDto medicoTratanteQueryDto) {
        return findByQueryDto(medicoTratanteQueryDto, false);
    }

    public PanacheQuery<MedicoTratante> findByQueryDto(MedicoTratanteQueryDto medicoTratanteQueryDto, boolean onlyNoCliente) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (medicoTratanteQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", medicoTratanteQueryDto.id);
        }
        if (onlyNoCliente) {
            query.append(" and persona.cliente IS NULL");
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
        if (medicoTratanteQueryDto.estado != null) {
            String q = switch (medicoTratanteQueryDto.estado) {
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

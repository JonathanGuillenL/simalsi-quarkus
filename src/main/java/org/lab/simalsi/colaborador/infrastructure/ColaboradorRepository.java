package org.lab.simalsi.colaborador.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.colaborador.application.ColaboradorQueryDto;
import org.lab.simalsi.colaborador.models.Colaborador;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class ColaboradorRepository implements PanacheRepository<Colaborador> {

    public Optional<Colaborador> findColaboradorByUsername(String username) {
        return find("username", username).stream().findFirst();
    }

    public PanacheQuery<Colaborador> findByQueryDto(ColaboradorQueryDto colaboradorQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (colaboradorQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", colaboradorQueryDto.id);
        }
        if (colaboradorQueryDto.nombres != null) {
            query.append(" and nombres like :nombre or apellidos like :nombre");
            params.put("nombre", "%" + colaboradorQueryDto.nombres + "%");
        }
        if (colaboradorQueryDto.numeroIdentificacion != null) {
            query.append(" and numeroIdentificacion = :numeroIdentificacion");
            params.put("numeroIdentificacion", colaboradorQueryDto.numeroIdentificacion);
        }
        if (colaboradorQueryDto.codigoSanitario != null) {
            query.append(" and codigoSanitario = :codigoSanitario");
            params.put("codigoSanitario", colaboradorQueryDto.codigoSanitario);
        }
        if (colaboradorQueryDto.email != null) {
            query.append(" and email = :email");
            params.put("email", colaboradorQueryDto.email);
        }
        if (colaboradorQueryDto.cargoId != null) {
            query.append(" and cargo.id = :cargoId");
            params.put("cargoId", colaboradorQueryDto.cargoId);
        }
        if (colaboradorQueryDto.estado != null) {
            String q = switch (colaboradorQueryDto.estado) {
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

package org.lab.simalsi.factura.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.factura.application.MetodoPagoQueryDto;
import org.lab.simalsi.factura.models.MetodoPago;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MetodoPagoRepository implements PanacheRepository<MetodoPago> {

    public PanacheQuery<MetodoPago> findByQueryDto(MetodoPagoQueryDto metodoPagoQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (metodoPagoQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", metodoPagoQueryDto.id);
        }
        if (metodoPagoQueryDto.descripcion != null) {
            query.append(" and descripcion like :descripcion");
            params.put("descripcion", "%" + metodoPagoQueryDto.descripcion + "%");
        }
        if (metodoPagoQueryDto.estado != null) {
            String q = switch (metodoPagoQueryDto.estado) {
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

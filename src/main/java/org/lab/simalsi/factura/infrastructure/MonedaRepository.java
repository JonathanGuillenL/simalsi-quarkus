package org.lab.simalsi.factura.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.factura.application.MonedaQueryDto;
import org.lab.simalsi.factura.models.Moneda;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MonedaRepository implements PanacheRepository<Moneda> {

    public PanacheQuery<Moneda> findByQueryDto(MonedaQueryDto monedaQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (monedaQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", monedaQueryDto.id);
        }
        if (monedaQueryDto.descripcion != null) {
            query.append(" and descripcion like :descripcion");
            params.put("descripcion", "%" + monedaQueryDto.descripcion + "%");
        }
        if (monedaQueryDto.estado != null) {
            String q = switch (monedaQueryDto.estado) {
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

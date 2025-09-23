package org.lab.simalsi.factura.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.factura.application.DescuentoQueryDto;
import org.lab.simalsi.factura.models.Descuento;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DescuentoRepository implements PanacheRepository<Descuento> {

    public PanacheQuery<Descuento> findByQueryDto(DescuentoQueryDto descuentoQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (descuentoQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", descuentoQueryDto.id);
        }
        if (descuentoQueryDto.descripcion != null) {
            query.append(" and descripcion like :descripcion");
            params.put("descripcion", "%" + descuentoQueryDto.descripcion + "%");
        }
        if (descuentoQueryDto.fecha != null) {
            query.append(" and :fecha >= fechaInicio and :fecha <= fechaFin");
            params.put("fecha", descuentoQueryDto.fecha);
        }
        if (descuentoQueryDto.estado != null) {
            String q = switch (descuentoQueryDto.estado) {
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

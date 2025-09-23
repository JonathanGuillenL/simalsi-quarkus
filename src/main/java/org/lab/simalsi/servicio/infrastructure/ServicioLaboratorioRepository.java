package org.lab.simalsi.servicio.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.servicio.application.ServicioQueryDto;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ServicioLaboratorioRepository implements PanacheRepository<ServicioLaboratorio> {

    public PanacheQuery<ServicioLaboratorio> findByQueryDto(ServicioQueryDto servicioQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (servicioQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", servicioQueryDto.id);
        }
        if (servicioQueryDto.descripcion != null) {
            query.append(" and descripcion like :descripcion");
            params.put("descripcion", "%" + servicioQueryDto.descripcion + "%");
        }
        if (servicioQueryDto.regionAnatomica != null) {
            query.append(" and procedimientoQuirurgico.regionAnatomica.descripcion like :regionAnatomica");
            params.put("regionAnatomica", "%" + servicioQueryDto.regionAnatomica + "%");
        }
        if (servicioQueryDto.procedimientoId != null) {
            query.append(" and procedimientoQuirurgico.id = :procedimientoId");
            params.put("procedimientoId", servicioQueryDto.procedimientoId);
        }
        if (servicioQueryDto.estado != null) {
            String q = switch (servicioQueryDto.estado) {
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

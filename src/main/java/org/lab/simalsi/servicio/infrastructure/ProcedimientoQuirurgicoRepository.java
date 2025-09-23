package org.lab.simalsi.servicio.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.servicio.application.ProcedimientoQuirurgicoQueryDto;
import org.lab.simalsi.servicio.models.ProcedimientoQuirurgico;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class ProcedimientoQuirurgicoRepository implements PanacheRepository<ProcedimientoQuirurgico> {

    public Optional<ProcedimientoQuirurgico> findByDescripcion(String descripcion) {
        var upper = descripcion.trim().toUpperCase();
        return find("UPPER(descripcion)", upper).firstResultOptional();
    }

    public PanacheQuery<ProcedimientoQuirurgico> findByRegionId(Long regionId) {
        return find("regionAnatomica.id", regionId);
    }

    public PanacheQuery<ProcedimientoQuirurgico> findByQueryDto(ProcedimientoQuirurgicoQueryDto procedimientoQuirurgicoQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (procedimientoQuirurgicoQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", procedimientoQuirurgicoQueryDto.id);
        }
        if (procedimientoQuirurgicoQueryDto.descripcion != null) {
            query.append(" and descripcion like :descripcion");
            params.put("descripcion", "%" + procedimientoQuirurgicoQueryDto.descripcion + "%");
        }
        if (procedimientoQuirurgicoQueryDto.regionId != null) {
            query.append(" and regionAnatomica.id = :regionId");
            params.put("regionId", procedimientoQuirurgicoQueryDto.regionId);
        }

        if (!params.isEmpty()) {
            return find(query.toString(), Sort.ascending("id"), params);
        }

        return find(query.toString(), Sort.ascending("id"));
    }
}

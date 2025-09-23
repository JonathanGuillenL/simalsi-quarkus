package org.lab.simalsi.servicio.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.servicio.application.RegionAnatomicaQueryDto;
import org.lab.simalsi.servicio.models.RegionAnatomica;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class RegionAnatomicaRepository implements PanacheRepository<RegionAnatomica> {

    public Optional<RegionAnatomica> findByDescripcion(String descripcion) {
        var upper = descripcion.trim().toUpperCase();
        return find("UPPER(descripcion)", upper).firstResultOptional();
    }

    public PanacheQuery<RegionAnatomica> findByQueryDto(RegionAnatomicaQueryDto regionAnatomicaQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (regionAnatomicaQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", regionAnatomicaQueryDto.id);
        }
        if (regionAnatomicaQueryDto.descripcion != null) {
            query.append(" and descripcion like :descripcion");
            params.put("descripcion", "%" + regionAnatomicaQueryDto.descripcion + "%");
        }

        if (!params.isEmpty()) {
            return find(query.toString(), Sort.ascending("id"), params);
        }

        return find(query.toString(), Sort.ascending("id"));
    }
}

package org.lab.simalsi.factura.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.factura.application.FacturaQueryDto;
import org.lab.simalsi.factura.models.Factura;
import org.lab.simalsi.solicitud.application.SolicitudCGOQueryDto;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FacturaRepository implements PanacheRepository<Factura> {

    public PanacheQuery<Factura> findByQueryDto(FacturaQueryDto facturaQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (facturaQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", facturaQueryDto.id);
        }
        if (facturaQueryDto.clienteId != null) {
            query.append(" and cliente.id = :clienteId");
            params.put("clienteId", facturaQueryDto.clienteId);
        }
        if (facturaQueryDto.clienteNombre != null) {
            query.append(" and cliente.persona.nombre like :clienteNombre or cliente.persona.apellido like :clienteNombre");
            params.put("clienteNombre", "%" + facturaQueryDto.clienteNombre + "%");
        }
        if (facturaQueryDto.fechaInicio != null && facturaQueryDto.fechaFin != null) {
            query.append(" and createdAt between :fechaInicio and :fechaFin");
            params.put("fechaInicio", facturaQueryDto.fechaInicio.atStartOfDay());
            params.put("fechaFin", facturaQueryDto.fechaFin.plusDays(1).atStartOfDay());
        }

        if (!params.isEmpty()) {
            return find(query.toString(), params);
        }

        return findAll();
    }
}

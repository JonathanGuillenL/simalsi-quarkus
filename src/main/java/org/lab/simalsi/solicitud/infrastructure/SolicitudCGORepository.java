package org.lab.simalsi.solicitud.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.home.ServicioRank;
import org.lab.simalsi.home.SolicitudCountDto;
import org.lab.simalsi.solicitud.application.SolicitudCGOQueryDto;
import org.lab.simalsi.solicitud.models.SolicitudCGO;
import org.lab.simalsi.solicitud.models.SolicitudEstado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class SolicitudCGORepository implements PanacheRepository<SolicitudCGO> {

    public PanacheQuery<SolicitudCGO> findByEstado(SolicitudEstado estado) {
        return find("estado", estado);
    }

    public PanacheQuery<SolicitudCGO> findByClienteIdAndEstado(Long clienteId, SolicitudEstado estado) {
        return find("cliente.id=?1 and estado=?2 and deletedAt is null", clienteId, estado);
    }

    public List<SolicitudCountDto> countLast12Month() {
        return getEntityManager().createQuery("""
            SELECT 
                EXTRACT(YEAR FROM s.fechaSolicitud) as year,
                EXTRACT(MONTH FROM s.fechaSolicitud) as month,
                COUNT(s)
            FROM SolicitudCGO s
            WHERE s.fechaSolicitud >= :inicio
            GROUP BY year, month
            ORDER BY year, month
        """, SolicitudCountDto.class)
            .setParameter("inicio", LocalDate.now().minusMonths(12).withDayOfMonth(1).atStartOfDay())
            .getResultList();
    }

    public List<SolicitudCountDto> countLast12MonthByRecepcionista(String userId) {
        return getEntityManager().createQuery("""
            SELECT 
                EXTRACT(YEAR FROM s.fechaSolicitud) as year,
                EXTRACT(MONTH FROM s.fechaSolicitud) as month,
                COUNT(s)
            FROM SolicitudCGO s
            WHERE s.fechaSolicitud >= :inicio
            AND s.recepcionista = :recepcionista
            GROUP BY year, month
            ORDER BY year, month
        """, SolicitudCountDto.class)
            .setParameter("inicio", LocalDate.now().minusMonths(12).withDayOfMonth(1).atStartOfDay())
            .setParameter("recepcionista", userId)
            .getResultList();
    }

    public List<SolicitudCountDto> countLast12MonthByCliente(Long clienteId) {
        return getEntityManager().createQuery("""
            SELECT 
                EXTRACT(YEAR FROM s.fechaSolicitud) as year,
                EXTRACT(MONTH FROM s.fechaSolicitud) as month,
                COUNT(s)
            FROM SolicitudCGO s
            WHERE s.fechaSolicitud >= :inicio
            AND s.cliente.id = :clienteId
            GROUP BY year, month
            ORDER BY year, month
        """, SolicitudCountDto.class)
            .setParameter("inicio", LocalDate.now().minusMonths(12).withDayOfMonth(1).atStartOfDay())
            .setParameter("clienteId", clienteId)
            .getResultList();
    }

    public List<ServicioRank> solicitudesRankedByServicio() {
        List<Object[]> counts = getEntityManager()
            .createQuery("""
                    SELECT s.servicioLaboratorio.descripcion, COUNT(s)
                    FROM SolicitudCGO s
                    GROUP BY s.servicioLaboratorio.descripcion
                    ORDER BY COUNT(s) DESC
                """, Object[].class)
            .getResultList();

        List<ServicioRank> result = counts.stream()
            .limit(4)
            .map(r -> new ServicioRank((String) r[0], (Long) r[1]))
            .toList();

        long otros = counts.stream()
            .skip(4)
            .mapToLong(r -> (Long) r[1])
            .sum();

        if (otros > 0) {
            result = new ArrayList<>(result);
            result.add(new ServicioRank("otros", otros));
        }

        return result;
    }

    public List<ServicioRank> solicitudesRankedByServicioByClienteId(Long clienteId) {
        List<Object[]> counts = getEntityManager()
            .createQuery("""
                    SELECT s.servicioLaboratorio.descripcion, COUNT(s)
                    FROM SolicitudCGO s
                    WHERE s.cliente.id = :clienteId
                    GROUP BY s.servicioLaboratorio.descripcion
                    ORDER BY COUNT(s) DESC
                """, Object[].class)
            .setParameter("clienteId", clienteId)
            .getResultList();

        List<ServicioRank> result = counts.stream()
            .limit(4)
            .map(r -> new ServicioRank((String) r[0], (Long) r[1]))
            .toList();

        long otros = counts.stream()
            .skip(4)
            .mapToLong(r -> (Long) r[1])
            .sum();

        if (otros > 0) {
            result = new ArrayList<>(result);
            result.add(new ServicioRank("otros", otros));
        }

        return result;
    }

    public PanacheQuery<SolicitudCGO> findByQueryDto(SolicitudCGOQueryDto solicitudCGOQueryDto) {
        StringBuilder query = new StringBuilder("1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (solicitudCGOQueryDto.id != null) {
            query.append(" and id = :id");
            params.put("id", solicitudCGOQueryDto.id);
        }
        if (solicitudCGOQueryDto.pacienteId != null) {
            query.append(" and paciente.id = :pacienteId");
            params.put("pacienteId", solicitudCGOQueryDto.pacienteId);
        }
        if (solicitudCGOQueryDto.clienteId != null) {
            query.append(" and cliente.id = :clienteId");
            params.put("clienteId", solicitudCGOQueryDto.clienteId);
        }
        if (solicitudCGOQueryDto.medicoTratanteId != null) {
            query.append(" and medicoTratante.id = :medicoTratanteId");
            params.put("medicoTratanteId", solicitudCGOQueryDto.medicoTratanteId);
        }
        if (solicitudCGOQueryDto.medicoNombre != null) {
            query.append(" and medicoTratante.persona.nombre like :medicoNombre or medicoTratante.persona.apellido like :medicoNombre");
            params.put("medicoNombre", "%" + solicitudCGOQueryDto.medicoNombre + "%");
        }
        if (solicitudCGOQueryDto.pacienteNombre != null) {
            query.append(" and paciente.persona.nombre like :pacienteNombre or paciente.persona.apellido like :pacienteNombre");
            params.put("pacienteNombre", "%" + solicitudCGOQueryDto.pacienteNombre + "%");
        }
        if (solicitudCGOQueryDto.clienteNombre != null) {
            query.append(" and cliente.persona.nombre like :clienteNombre or cliente.persona.apellido like :clienteNombre");
            params.put("clienteNombre", "%" + solicitudCGOQueryDto.clienteNombre + "%");
        }
        if (solicitudCGOQueryDto.procedimiento != null) {
            query.append(" and servicioLaboratorio.descripcion like :procedimiento");
            params.put("procedimiento", "%" + solicitudCGOQueryDto.procedimiento + "%");
        }
        if (solicitudCGOQueryDto.patologo != null) {
            query.append(" and resultadoCGO.patologo = :patologo");
            params.put("patologo", solicitudCGOQueryDto.patologo);
        }
        if (solicitudCGOQueryDto.histotecnologo != null) {
            query.append(" and muestra.histotecnologo = :histotecnologo");
            params.put("histotecnologo", solicitudCGOQueryDto.histotecnologo);
        }
        if (solicitudCGOQueryDto.servicioId != null) {
            query.append(" and servicioLaboratorio.id = :servicioId");
            params.put("servicioId", solicitudCGOQueryDto.servicioId);
        }
        if (solicitudCGOQueryDto.fechaInicio != null && solicitudCGOQueryDto.fechaFin != null) {
            query.append(" and fechaSolicitud between :fechaInicio and :fechaFin");
            params.put("fechaInicio", solicitudCGOQueryDto.fechaInicio.atStartOfDay());
            params.put("fechaFin", solicitudCGOQueryDto.fechaFin.plusDays(1).atStartOfDay().plusSeconds(-1));
        }
        if (solicitudCGOQueryDto.solicitudEstado != null) {
            query.append(" and estado = :estado");
            params.put("estado", solicitudCGOQueryDto.solicitudEstado);
        }

        if (!params.isEmpty()) {
            return find(query.toString(), Sort.ascending("id"), params);
        }

        return find(query.toString(), Sort.ascending("id"));
    }
}

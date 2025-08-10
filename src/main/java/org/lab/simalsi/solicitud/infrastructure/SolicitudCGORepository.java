package org.lab.simalsi.solicitud.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.solicitud.models.SolicitudCGO;
import org.lab.simalsi.solicitud.models.SolicitudEstado;

@ApplicationScoped
public class SolicitudCGORepository implements PanacheRepository<SolicitudCGO> {

    public PanacheQuery<SolicitudCGO> findByEstado(SolicitudEstado estado) {
        return find("estado", estado);
    }

    public PanacheQuery<SolicitudCGO> findByClienteIdAndEstado(Long clienteId, SolicitudEstado estado) {
        return find("cliente.id=?1 and estado=?2", clienteId, estado);
    }
}

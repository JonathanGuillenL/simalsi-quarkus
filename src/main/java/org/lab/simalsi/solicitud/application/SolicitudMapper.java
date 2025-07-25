package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

@ApplicationScoped
public class SolicitudMapper {

    public SolicitudCGO toModel(CrearSolicitudCGODto solicitudCGODto) {
        var solicitudCGO = new SolicitudCGO();
        solicitudCGO.setObservaciones(solicitudCGODto.observaciones());

        return solicitudCGO;
    }
}

package org.lab.simalsi.solicitud.application;

import org.lab.simalsi.solicitud.models.SolicitudEstado;

import java.time.LocalDateTime;

public record SolicitudPageResponse(
    Long id,
    LocalDateTime fechaSolicitud,
    String paciente,
    String medicoTratante,
    String cliente,
    String servicio,
    Double precio,
    SolicitudEstado solicitudEstado,
    LocalDateTime deletedAt
) {
}

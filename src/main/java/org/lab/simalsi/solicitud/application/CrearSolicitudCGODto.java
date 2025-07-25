package org.lab.simalsi.solicitud.application;

public record CrearSolicitudCGODto(
    Long recepcionistaId,
    Long clienteId,
    Long pacienteId,
    String observaciones
) {
}

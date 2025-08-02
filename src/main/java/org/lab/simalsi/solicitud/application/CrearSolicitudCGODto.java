package org.lab.simalsi.solicitud.application;

public record CrearSolicitudCGODto(
    Long clienteId,
    Long pacienteId,
    Long medicoTratanteId,
    Long servicioLaboratorioId,
    String observaciones
) {
}

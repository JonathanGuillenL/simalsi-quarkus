package org.lab.simalsi.solicitud.application;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CrearSolicitudCGODto(
    @NotNull(message = "Cliente es requerido.") Long clienteId,
    @NotNull(message = "Paciente es requerido.") Long pacienteId,
    Long medicoTratanteId,
    @NotNull(message = "El campo fecha de toma de muestra es requerido.") LocalDateTime fechaTomaMuestra,
    @NotNull(message = "Servicio es requerido.") Long servicioLaboratorioId,
    String observaciones
) {
}

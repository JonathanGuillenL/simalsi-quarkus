package org.lab.simalsi.solicitud.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearResultadoCGODto(
    Long cie,
    @NotBlank(message = "El campo diagnóstico es requerido.") String diagnostico
) {
}

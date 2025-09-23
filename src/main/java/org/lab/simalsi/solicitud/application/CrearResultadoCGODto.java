package org.lab.simalsi.solicitud.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearResultadoCGODto(
    @NotNull(message = "El campo código de enfermedad es requerido.") Long cie,
    @NotBlank(message = "El campo diagnóstico es requerido.") String diagnostico
) {
}

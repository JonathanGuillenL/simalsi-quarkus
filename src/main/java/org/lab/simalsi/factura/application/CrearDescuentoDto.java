package org.lab.simalsi.factura.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CrearDescuentoDto(
    @NotBlank(message = "El campo descripción es requerido.") String descripcion,
    @NotNull(message = "El campo porcentaje es requerido.") Double porcentaje,
    @NotNull(message = "El campo fecha de inicio es requerido.") LocalDate fechaInicio,
    @NotNull(message = "El campo fecha de fin es requerido.") LocalDate fechaFin,
    @NotNull(message = "El campo anual es requerido.") Boolean anual,
    @NotNull(message = "El campo automático es requerido.") Boolean automatico
) {
}

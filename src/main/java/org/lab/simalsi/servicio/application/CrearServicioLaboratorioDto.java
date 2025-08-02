package org.lab.simalsi.servicio.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearServicioLaboratorioDto(
    @NotBlank(message = "el campo descripcion es requerido.") String descripcion,
    @NotNull(message = "el campo precio es requerido.") Double precio,
    @NotNull(message = "Procedimiento quirurgico es requerido.") Long procedimientoId
) {
}

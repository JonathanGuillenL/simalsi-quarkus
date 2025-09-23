package org.lab.simalsi.servicio.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearProcedimientoQuirurgicoDto(
    @NotBlank(message = "El campo descripción es requerido.") String descripcion,
    @NotNull(message = "El campo region anátomica es requerido.") Long regionAnatomicaId
) {
}

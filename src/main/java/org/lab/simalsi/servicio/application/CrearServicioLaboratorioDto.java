package org.lab.simalsi.servicio.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearServicioLaboratorioDto(
    @NotBlank(message = "El campo descripción es requerido.") String descripcion,
    @NotNull(message = "El campo precio es requerido.") Double precio,
    @NotNull(message = "El campo región anatómica es requerido.") Long regionAnatomicaId,
    @NotNull(message = "El campo procedimiento quirúrgico es requerido.") Long procedimientoId
) {
}

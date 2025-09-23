package org.lab.simalsi.servicio.application;

import jakarta.validation.constraints.NotBlank;

public record CrearRegionAnatomicaDto(
    @NotBlank(message = "El campo descripción es requerido.") String descripcion
) {
}

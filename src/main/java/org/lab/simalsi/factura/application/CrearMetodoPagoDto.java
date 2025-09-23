package org.lab.simalsi.factura.application;

import jakarta.validation.constraints.NotBlank;

public record CrearMetodoPagoDto(
    @NotBlank(message = "El campo descripción es requerido.") String descripcion
) {
}

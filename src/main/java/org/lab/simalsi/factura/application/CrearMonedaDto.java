package org.lab.simalsi.factura.application;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearMonedaDto(
    @NotBlank(message = "El campo descripción es requerido.") String descripcion,
    @NotNull(message = "Tipo de cambio inicial es requerido.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Tipo de cambio debe ser mayor que cero.") Double tipoCambio,
    @NotBlank(message = "Signo monetario es requerido.") String signoMonetario
) {
}

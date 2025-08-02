package org.lab.simalsi.factura.application;

import jakarta.validation.constraints.NotNull;

public record PagoDto(
    @NotNull(message = "Monto es requerido.") Double monto,
    String observaciones,
    String referencia,
    @NotNull(message = "Metodo de pago es requerido.") Long metodoPagoId,
    @NotNull(message = "Moneda es requerido.") Long monedaId
) {
}

package org.lab.simalsi.factura.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CrearFacturaDto(
    List<Long> descuentos,
    List<Long> detalle,
    @NotNull(message = "El campo cliente es requerido.") Long clienteId,
    @Valid PagoDto pago
) {
}

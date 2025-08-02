package org.lab.simalsi.factura.application;

import jakarta.validation.Valid;

import java.util.List;

public record CrearFacturaDto(
    List<Long> descuentos,
    List<Long> detalle,
    Long clienteId,
    @Valid PagoDto pago
) {
}

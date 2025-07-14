package org.lab.simalsi.factura.application;

import java.util.List;

public record CrearFacturaDto(
    List<Long> descuentos,
    List<Long> detalle,
    Long clienteId
) {
}

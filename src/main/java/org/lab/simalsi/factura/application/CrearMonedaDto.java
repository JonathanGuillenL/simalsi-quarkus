package org.lab.simalsi.factura.application;

public record CrearMonedaDto(
    String descripcion,
    Double tipoCambio,
    String signoMonetario
) {
}

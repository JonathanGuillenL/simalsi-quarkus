package org.lab.simalsi.factura.application;

import java.time.LocalDate;

public record CrearDescuentoDto(
    String descripcion,
    Double porcentaje,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    Boolean anual,
    Boolean automatico
) {
}

package org.lab.simalsi.solicitud.application;

import java.time.LocalDate;
import java.util.List;

public record AgregarMuestraDto(
    LocalDate fechaIngreso,
    LocalDate fechaProcesamiento,
    Integer numeroDeCortes,
    Double pesoMuestra,
    String descripcionMacroscopica,
    List<AgregarLaminaDto> laminas
) {
}

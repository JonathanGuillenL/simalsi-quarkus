package org.lab.simalsi.solicitud.application;

import java.time.LocalDateTime;

public record AgregarMuestraDto(
    LocalDateTime fechaProcesamiento,
    Integer numeroDeCortes,
    Double pesoMuestra,
    String descripcionMacroscopica,
    Long solicitudId
) {
}

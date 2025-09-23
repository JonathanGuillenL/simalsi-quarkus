package org.lab.simalsi.solicitud.application;

public record LaminaResponseDto(
    Long id,
    Integer fila,
    Integer columna,
    Long cajaId
) {
}

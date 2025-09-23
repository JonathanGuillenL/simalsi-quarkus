package org.lab.simalsi.solicitud.application;

public record MoverLaminaDto(
    Integer fila,
    Integer columna,
    Long cajaId
) {
}

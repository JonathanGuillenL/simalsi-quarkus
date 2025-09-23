package org.lab.simalsi.solicitud.application;

import org.lab.simalsi.solicitud.models.Lamina;

import java.util.List;

public record CajaResponseDto(
    Long id,
    Integer numeroColumnas,
    Integer numeroFilas
) {
}

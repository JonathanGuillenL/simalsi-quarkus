package org.lab.simalsi.solicitud.application;

import org.lab.simalsi.colaborador.models.Colaborador;

import java.time.LocalDate;
import java.util.List;

public record MuestraResponseDto(
    Long id,
    LocalDate fechaIngreso,
    LocalDate fechaProcesamiento,
    Integer numeroDeCortes,
    Double pesoMuestra,
    String descripcionMacroscopica,
    Colaborador histotecnologo,
    List<LaminaResponseDto> laminas
) {
}

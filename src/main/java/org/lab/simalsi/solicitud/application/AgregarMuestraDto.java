package org.lab.simalsi.solicitud.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record AgregarMuestraDto(
    @NotNull(message = "El campo fecha de ingreso es requerido.") LocalDate fechaIngreso,
    @NotNull(message = "El campo fecha de procesamiento es requerido.") LocalDate fechaProcesamiento,
    @NotNull(message = "El campo número de cortes es requerido.") Integer numeroDeCortes,
    @NotNull(message = "El campo peso de muestra es requerido.") Double pesoMuestra,
    @NotBlank(message = "El campo descripción macroscopica es requerido.") String descripcionMacroscopica,
    List<AgregarLaminaDto> laminas
) {
}

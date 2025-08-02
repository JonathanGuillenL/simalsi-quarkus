package org.lab.simalsi.paciente.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CrearPacienteDto(
    @NotBlank(message = "El campo nombres es requerido.") String nombres,
    @NotBlank(message = "El campo apellidos es requerido.") String apellidos,
    @NotNull(message = "El campo nacimiento es requerido.") LocalDate nacimiento,
    @NotBlank(message = "El campo teléfono es requerido.") String telefono,
    @NotBlank(message = "El campo dirección es requerido.") String direccion
) {
}

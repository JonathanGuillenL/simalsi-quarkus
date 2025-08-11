package org.lab.simalsi.paciente.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.lab.simalsi.paciente.models.Sexo;

import java.time.LocalDate;

public record CrearPacienteDto(
    @NotBlank(message = "El campo nombres es requerido.") String nombres,
    @NotBlank(message = "El campo apellidos es requerido.") String apellidos,
    @NotNull(message = "El campo nacimiento es requerido.") LocalDate nacimiento,
    @NotNull(message = "El campo sexo es requerido.") Sexo sexo,
    @Pattern(regexp = "\\d{8}", message = "Debe contener exactamente 8 dígitos numéricos") String telefono,
    @NotBlank(message = "El campo dirección es requerido.") String direccion
) {
}

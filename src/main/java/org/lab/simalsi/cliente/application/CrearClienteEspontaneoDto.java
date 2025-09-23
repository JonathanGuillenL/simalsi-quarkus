package org.lab.simalsi.cliente.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.lab.simalsi.paciente.models.Sexo;

import java.time.LocalDate;

public record CrearClienteEspontaneoDto(
    @NotBlank(message = "El campo nombres es requerido.") String nombres,
    @NotBlank(message = "El campo apellidos es requerido.") String apellidos,
    @Pattern(
        regexp = "^[0-9]{3}-[0-9]{6}-[0-9]{4}[A-HJ-NP-WY]|$",
        message = "Formato de cédula no válido (use ###-######-####X)"
    )
    String cedula,
    String telefono,
    String direccion,
    @Email(message = "El correo electrónico no es válido.") String email,
    boolean hasPaciente,
    LocalDate nacimiento,
    Sexo sexo,
    Long pacienteId
) {
}

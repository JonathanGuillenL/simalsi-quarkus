package org.lab.simalsi.paciente.application;

import org.lab.simalsi.paciente.models.Sexo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PacienteResponseDto(
    Long id,
    String nombres,
    String apellidos,
    Long edad,
    LocalDate nacimiento,
    Sexo sexo,
    String telefono,
    String direccion,
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
}

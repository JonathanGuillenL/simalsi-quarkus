package org.lab.simalsi.paciente.application;

import java.time.LocalDateTime;

public record PacienteResponsePageDto(
    Long id,
    String nombre,
    String sexo,
    Long edad,
    String nacimiento,
    String telefono,
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
}

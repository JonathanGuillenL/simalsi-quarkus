package org.lab.simalsi.paciente.application;

public record PacienteResponsePageDto(
    Long id,
    String nombre,
    String sexo,
    Long edad,
    String nacimiento,
    String telefono
) {
}

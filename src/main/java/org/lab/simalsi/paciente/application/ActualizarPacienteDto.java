package org.lab.simalsi.paciente.application;

import java.time.LocalDate;

public record ActualizarPacienteDto(
    LocalDate nacimiento,
    String telefono,
    String direccion
) {
}

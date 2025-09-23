package org.lab.simalsi.cliente.application;

import org.lab.simalsi.cliente.models.TipoCliente;
import org.lab.simalsi.paciente.models.Sexo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClienteEspontaneoResponseDto(
    Long id,
    String nombres,
    String apellidos,
    LocalDate nacimiento,
    Sexo sexo,
    String cedula,
    String telefono,
    String direccion,
    String email,
    String username,
    TipoCliente tipoCliente,
    Long pacienteId,
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
}

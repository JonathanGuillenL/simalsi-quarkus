package org.lab.simalsi.cliente.application;

import org.lab.simalsi.cliente.models.TipoCliente;
import org.lab.simalsi.paciente.models.Sexo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MedicoAfiliadoResponseDto(
    Long id,
    String nombres,
    String apellidos,
    String codigoSanitario,
    String cedula,
    String telefono,
    String direccion,
    String email,
    String username,
    Long medicoTratanteId,
    TipoCliente tipoCliente,
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
}

package org.lab.simalsi.cliente.application;

import org.lab.simalsi.cliente.models.TipoCliente;

import java.time.LocalDateTime;

public record ClinicaAfiliadaResponseDto(
    Long id,
    String nombre,
    String razonSocial,
    String telefono,
    String direccion,
    String email,
    String username,
    String ruc,
    Long departamentoId,
    Long municipioId,
    Long personaId,
    TipoCliente tipoCliente,
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
}

package org.lab.simalsi.medico.application;

import java.time.LocalDateTime;

public record MedicoTratanteResponseDto(
    Long id,
    String nombres,
    String apellidos,
    String codigoSanitario,
    String telefono,
    String direccion,
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
}

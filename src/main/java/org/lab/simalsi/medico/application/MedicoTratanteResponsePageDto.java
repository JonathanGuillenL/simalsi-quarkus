package org.lab.simalsi.medico.application;

import java.time.LocalDateTime;

public record MedicoTratanteResponsePageDto(
    Long id,
    String nombre,
    String codigoSanitario,
    String telefono,
    LocalDateTime createdAt,
    LocalDateTime deletedAt
) {
}

package org.lab.simalsi.medico.application;

public record MedicoTratanteResponseDto(
    Long id,
    String nombres,
    String apellidos,
    String codigoSanitario,
    String telefono,
    String direccion
) {
}

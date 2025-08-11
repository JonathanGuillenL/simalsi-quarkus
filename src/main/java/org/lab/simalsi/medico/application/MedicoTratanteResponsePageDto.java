package org.lab.simalsi.medico.application;

public record MedicoTratanteResponsePageDto(
    Long id,
    String nombre,
    String codigoSanitario,
    String telefono
) {
}

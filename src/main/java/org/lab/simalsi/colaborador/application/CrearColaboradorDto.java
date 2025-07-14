package org.lab.simalsi.colaborador.application;

public record CrearColaboradorDto(
    String nombres,
    String apellidos,
    String numeroIdentificacion,
    String telefono,
    String username,
    String email,
    Long cargoId
) {
}

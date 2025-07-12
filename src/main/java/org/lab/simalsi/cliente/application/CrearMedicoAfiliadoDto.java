package org.lab.simalsi.cliente.application;

public record CrearMedicoAfiliadoDto(
    String nombres,
    String apellidos,
    String cedula,
    String telefono,
    String codigoSanitario,
    String username,
    String email,
    Long tipoId
) {
}

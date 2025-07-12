package org.lab.simalsi.cliente.application;

public record CrearClienteEspontaneoDto(
    String nombres,
    String apellidos,
    String cedula,
    String telefono,
    String username,
    String email,
    Long tipoId
) {
}

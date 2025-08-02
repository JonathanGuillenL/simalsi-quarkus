package org.lab.simalsi.cliente.application;

public record CrearClienteJuridicoDto(
    String nombre,
    String razoSocial,
    String telefono,
    String direccion,
    String username,
    String email
) {
}

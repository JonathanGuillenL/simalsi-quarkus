package org.lab.simalsi.cliente.application;

public record CrearClienteNaturalDto(
    String nombres,
    String apellidos,
    String cedula,
    String telefono,
    String direccion,
    String username,
    String email
) {
}

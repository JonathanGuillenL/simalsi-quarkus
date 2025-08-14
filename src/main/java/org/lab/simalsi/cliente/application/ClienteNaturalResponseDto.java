package org.lab.simalsi.cliente.application;

public record ClienteNaturalResponseDto(
    Long id,
    String nombres,
    String apellidos,
    String cedula,
    String telefono,
    String direccion,
    String email,
    String usuario
) {
}

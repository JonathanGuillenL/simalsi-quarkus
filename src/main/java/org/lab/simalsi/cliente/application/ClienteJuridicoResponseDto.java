package org.lab.simalsi.cliente.application;

public record ClienteJuridicoResponseDto(
    Long id,
    String nombre,
    String razonSocial,
    String telefono,
    String direccion,
    String email,
    String usuario,
    String ruc
) {
}

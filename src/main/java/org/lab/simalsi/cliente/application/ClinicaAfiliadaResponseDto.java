package org.lab.simalsi.cliente.application;

import org.lab.simalsi.cliente.models.TipoCliente;

public record ClienteJuridicoResponseDto(
    Long id,
    String nombre,
    String razonSocial,
    String telefono,
    String direccion,
    String email,
    String usuario,
    String ruc,
    TipoCliente tipoCliente
) {
}

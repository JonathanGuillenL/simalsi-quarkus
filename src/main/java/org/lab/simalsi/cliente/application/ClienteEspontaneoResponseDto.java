package org.lab.simalsi.cliente.application;

import org.lab.simalsi.cliente.models.TipoCliente;

public record ClienteNaturalResponseDto(
    Long id,
    String nombres,
    String apellidos,
    String cedula,
    String telefono,
    String direccion,
    String email,
    String usuario,
    TipoCliente tipoCliente
) {
}

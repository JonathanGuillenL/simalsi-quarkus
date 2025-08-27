package org.lab.simalsi.cliente.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.lab.simalsi.cliente.models.TipoCliente;

public record CrearClienteJuridicoDto(
    @NotBlank(message = "El campo nombres es requerido.") String nombre,
    @NotNull(message = "Tipo de clienet es requerido.") TipoCliente tipoCliente,
    @NotBlank(message = "El campo razón social es requerido.") String razonSocial,
    @NotBlank(message = "El campo teléfono es requerido.") String telefono,
    @NotBlank(message = "El campo dirección es requerido.") String direccion,
    @NotBlank(message = "El campo email es requerido.") String email,
    @NotBlank(message = "El campo RUC es requerido.") String ruc
) {
}

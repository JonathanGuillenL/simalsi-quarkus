package org.lab.simalsi.cliente.application;

import jakarta.validation.constraints.NotBlank;

public record CrearClienteJuridicoDto(
    @NotBlank(message = "El campo nombres es requerido.") String nombre,
    @NotBlank(message = "El campo razón social es requerido.") String razonSocial,
    @NotBlank(message = "El campo teléfono es requerido.") String telefono,
    @NotBlank(message = "El campo dirección es requerido.") String direccion,
    @NotBlank(message = "El campo email es requerido.") String email,
    @NotBlank(message = "El campo RUC es requerido.") String ruc
) {
}

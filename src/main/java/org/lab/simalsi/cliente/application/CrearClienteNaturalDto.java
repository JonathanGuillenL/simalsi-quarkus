package org.lab.simalsi.cliente.application;

import jakarta.validation.constraints.NotBlank;

public record CrearClienteNaturalDto(
    @NotBlank(message = "El campo nombres es requerido.") String nombres,
    @NotBlank(message = "El campo apellidos es requerido.") String apellidos,
    @NotBlank(message = "El campo cédula es requerido.") String cedula,
    @NotBlank(message = "El campo teléfono es requerido.") String telefono,
    @NotBlank(message = "El campo dirección es requerido.") String direccion,
    @NotBlank(message = "El campo email es requerido.") String email
) {
}

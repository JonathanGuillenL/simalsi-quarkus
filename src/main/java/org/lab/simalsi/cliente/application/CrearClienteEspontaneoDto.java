package org.lab.simalsi.cliente.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.lab.simalsi.cliente.models.TipoCliente;

public record CrearClienteNaturalDto(
    @NotBlank(message = "El campo nombres es requerido.") String nombres,
    @NotBlank(message = "El campo apellidos es requerido.") String apellidos,
    @NotNull(message = "Tipo de clienet es requerido.") TipoCliente tipoCliente,
    @NotBlank(message = "El campo cédula es requerido.") String cedula,
    @NotBlank(message = "El campo teléfono es requerido.") String telefono,
    @NotBlank(message = "El campo dirección es requerido.") String direccion,
    @Email(message = "El correo electróno no es válido.") String email
) {
}

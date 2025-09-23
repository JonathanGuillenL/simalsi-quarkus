package org.lab.simalsi.cliente.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CrearMedicoAfiliado(
    @NotBlank(message = "El campo nombres es requerido.") String nombres,
    @NotBlank(message = "El campo apellidos es requerido.") String apellidos,
    @NotBlank(message = "El campo código sanitario es requerido.") String codigoSanitario,
    @NotBlank(message = "El campo cédula es requerido.")
    @Pattern(
        regexp = "^[0-9]{3}-[0-9]{6}-[0-9]{4}[A-HJ-NP-WY]$",
        message = "Formato de cédula no válido (use ###-######-####X)"
    )
    String cedula,
    @NotBlank(message = "El campo teléfono es requerido.") String telefono,
    @NotBlank(message = "El campo dirección es requerido.") String direccion,
    @Email(message = "El correo electrónico no es válido.") String email,
    boolean hasMedicoTratante,
    Long medicoTratanteId
) {
}

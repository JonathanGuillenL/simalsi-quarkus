package org.lab.simalsi.colaborador.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CrearColaboradorDto(
    @NotBlank(message = "El campo nombres es requerido.") String nombres,
    @NotBlank(message = "El campo apellidos es requerido.") String apellidos,
    @NotBlank(message = "El campo número de identificación es requerido.")
    @Pattern(
        regexp = "^[0-9]{3}-[0-9]{6}-[0-9]{4}[A-HJ-NP-WY]$",
        message = "Formato de cédula no válido (use ###-######-####X)"
    )
    String numeroIdentificacion,
    String codigoSanitario,
    @NotBlank(message = "El campo teléfono es requerido.") String telefono,
    boolean user,
    @NotBlank(message = "El campo email es requerido.") String email,
    @NotNull(message = "El campo cargo es requerido.") Long cargoId
) {
}

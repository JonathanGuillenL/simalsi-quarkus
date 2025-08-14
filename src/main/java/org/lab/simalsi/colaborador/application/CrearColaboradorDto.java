package org.lab.simalsi.colaborador.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearColaboradorDto(
    @NotBlank(message = "El campo nombres es requerido.") String nombres,
    @NotBlank(message = "El campo apellidos es requerido.") String apellidos,
    @NotBlank(message = "El campo número de identificación es requerido.") String numeroIdentificacion,
    @NotBlank(message = "El campo teléfono es requerido.") String telefono,
    boolean user,
    @NotBlank(message = "El campo email es requerido.") String email,
    @NotNull(message = "El campo cargo es requerido.") Long cargoId
) {
}

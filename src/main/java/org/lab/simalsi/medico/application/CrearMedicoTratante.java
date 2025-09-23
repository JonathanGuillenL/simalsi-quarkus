package org.lab.simalsi.medico.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CrearMedicoTratante(
    @NotBlank(message = "El campo nombres es requerido.") String nombres,
    @NotBlank(message = "El campo apellidos es requerido.") String apellidos,
    @Pattern(regexp = "(\\d{5})?", message = "Debe contener exactamente 5 dígitos")
    @NotBlank(message = "El campo código sanitario es requerido.") String codigoSanitario,
    @Pattern(regexp = "(\\d{8})?", message = "Debe contener exactamente 8 dígitos")
    @NotBlank(message = "El campo teléfono es requerido.") String telefono,
    String direccion
) {
}

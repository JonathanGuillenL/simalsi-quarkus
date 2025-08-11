package org.lab.simalsi.medico.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CrearMedicoTratante(
    @NotBlank(message = "El campo nombre es requerido.") String nombres,
    @NotBlank(message = "El campo apellido es requerido.") String apellidos,
    String numeroIdentificacion,
    @NotBlank(message = "El campo código sanitario es requerido.") String codigoSanitario,
    @Pattern(regexp = "\\d{8}", message = "Debe contener exactamente 8 dígitos numéricos") String telefono,
    String direccion
) {
}

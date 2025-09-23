package org.lab.simalsi.cliente.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearClinicaAfiliada(
    @NotBlank(message = "El campo nombre es requerido.") String nombre,
    String razonSocial,
    @NotBlank(message = "El campo teléfono es requerido.") String telefono,
    @NotBlank(message = "El campo dirección es requerido.") String direccion,
    @Email(message = "El correo electróno no es válido.") String email,
    @NotBlank(message = "El campo RUC es requerido.") String ruc,
    @NotNull(message = "El campo departamento es requerido.") Long departamentoId,
    @NotNull(message = "El campo municipio es requerido.") Long municipioId
) {
}

package org.lab.simalsi.cliente.application;

public record CrearClinicaAfiliada(
    String nombre,
    String telefono,
    String direccion,
    String username,
    String email,
    Long tipoId
) {
}

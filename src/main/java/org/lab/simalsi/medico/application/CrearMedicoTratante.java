package org.lab.simalsi.medico.application;

public record CrearMedicoTratante(
    String nombres,
    String apellidos,
    String numeroIdentificacion,
    String codigoSanitario,
    String telefono,
    String direccion
) {
}

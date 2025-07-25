package org.lab.simalsi.cliente.models;

import jakarta.persistence.Entity;

@Entity
public class MedicoAfiliado extends Cliente {
    private String nombres;
    private String apellidos;
    private String cedula;
    private String codigoSanitario;

    public MedicoAfiliado() {
    }

    public MedicoAfiliado(Long id, String username, String email, String nombres, String apellidos, String cedula, String codigoSanitario) {
        super(id, username, email);
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.codigoSanitario = codigoSanitario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCodigoSanitario() {
        return codigoSanitario;
    }

    public void setCodigoSanitario(String codigoSanitario) {
        this.codigoSanitario = codigoSanitario;
    }
}

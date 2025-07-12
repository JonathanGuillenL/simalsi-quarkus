package org.lab.simalsi.cliente.models;

import jakarta.persistence.Entity;

@Entity
public class ClienteEspontaneo extends Cliente {
    private String nombres;
    private String apellidos;
    private String cedula;
    private String telefono;

    public ClienteEspontaneo() {
    }

    public ClienteEspontaneo(Long id, String username, String email, String nombres, String apellidos, String cedula, String telefono) {
        super(id, username, email);
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.telefono = telefono;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

package org.lab.simalsi.cliente.models;

import jakarta.persistence.Entity;

@Entity
public class ClinicaAfiliada extends Cliente {
    private String nombre;
    private String direccion;

    public ClinicaAfiliada() {
    }

    public ClinicaAfiliada(Long id, String username, String email, String nombre, String direccion) {
        super(id, username, email);
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}

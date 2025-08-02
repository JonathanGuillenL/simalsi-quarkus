package org.lab.simalsi.persona.models;

import jakarta.persistence.Entity;

@Entity
public class PersonaJuridica extends Persona {
    private String razonSocial;

    public PersonaJuridica() {
    }

    public PersonaJuridica(Long id, String username, String email, String nombre, String telefono, String direccion) {
        super(id, nombre, telefono, direccion);
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
}

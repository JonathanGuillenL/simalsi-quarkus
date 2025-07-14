package org.lab.simalsi.colaborador.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private boolean requiereCodigoSanitario;

    public Cargo() {
    }

    public Cargo(Long id, String nombre, boolean requiereCodigoSanitario) {
        this.id = id;
        this.nombre = nombre;
        this.requiereCodigoSanitario = requiereCodigoSanitario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isRequiereCodigoSanitario() {
        return requiereCodigoSanitario;
    }

    public void setRequiereCodigoSanitario(boolean requiereCodigoSanitario) {
        this.requiereCodigoSanitario = requiereCodigoSanitario;
    }
}

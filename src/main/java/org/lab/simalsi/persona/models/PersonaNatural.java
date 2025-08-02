package org.lab.simalsi.persona.models;

import jakarta.persistence.*;

@Entity
public class PersonaNatural extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String apellido;

    @Column(length = 50)
    private String numeroIdentificacion;

    public PersonaNatural() {
    }

    public PersonaNatural(Long id, String nombre, String apellido, String numeroIdentificacion, String telefono, String direccion) {
        super(id, nombre, telefono, direccion);
        this.apellido = apellido;
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
}

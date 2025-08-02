package org.lab.simalsi.cliente.models;

import jakarta.persistence.*;
import org.lab.simalsi.persona.models.Persona;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Persona persona;

    public Cliente() {
    }

    public Cliente(Long id, String username, String email, Persona persona) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.persona = persona;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}

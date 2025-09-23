package org.lab.simalsi.cliente.models;

import jakarta.persistence.*;
import org.lab.simalsi.persona.models.Persona;

import java.time.LocalDateTime;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Persona persona;

    @Convert(converter = TipoClienteConverter.class)
    private TipoCliente tipoCliente;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public Cliente() {
        this.createdAt = LocalDateTime.now();
    }

    public Cliente(Long id, String username, String email, Persona persona, TipoCliente tipoCliente, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.persona = persona;
        this.tipoCliente = tipoCliente;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
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

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}

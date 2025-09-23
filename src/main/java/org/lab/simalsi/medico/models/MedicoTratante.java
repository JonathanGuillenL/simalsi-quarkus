package org.lab.simalsi.medico.models;

import jakarta.persistence.*;
import org.lab.simalsi.persona.models.Persona;
import org.lab.simalsi.persona.models.PersonaNatural;

import java.time.LocalDateTime;

@Entity
public class MedicoTratante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String codigoSanitario;

    @OneToOne(cascade = CascadeType.ALL)
    private PersonaNatural persona;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public MedicoTratante() {
        this.createdAt = LocalDateTime.now();
    }

    public MedicoTratante(Long id, String codigoSanitario, PersonaNatural persona) {
        this.id = id;
        this.codigoSanitario = codigoSanitario;
        this.persona = persona;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoSanitario() {
        return codigoSanitario;
    }

    public void setCodigoSanitario(String codigoSanitario) {
        this.codigoSanitario = codigoSanitario;
    }

    public PersonaNatural getPersona() {
        return persona;
    }

    public void setPersona(PersonaNatural persona) {
        this.persona = persona;
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

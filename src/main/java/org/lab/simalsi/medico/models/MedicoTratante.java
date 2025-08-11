package org.lab.simalsi.medico.models;

import jakarta.persistence.*;
import org.lab.simalsi.persona.models.Persona;
import org.lab.simalsi.persona.models.PersonaNatural;

@Entity
public class MedicoTratante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String codigoSanitario;

    @OneToOne(cascade = CascadeType.ALL)
    private PersonaNatural persona;

    public MedicoTratante() {
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
}

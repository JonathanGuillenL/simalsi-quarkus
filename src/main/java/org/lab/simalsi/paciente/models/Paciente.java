package org.lab.simalsi.paciente.models;

import jakarta.persistence.*;
import org.lab.simalsi.persona.models.PersonaNatural;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate nacimiento;

    @Convert(converter = SexoConverter.class)
    private Sexo sexo;

    @OneToOne(cascade = CascadeType.ALL)
    private PersonaNatural persona;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public Paciente() {
        this.createdAt = LocalDateTime.now();
    }

    public Paciente(Long id, LocalDate nacimiento, Sexo sexo, PersonaNatural persona, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.nacimiento = nacimiento;
        this.sexo = sexo;
        this.persona = persona;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Long getEdad() {
        return ChronoUnit.YEARS.between(nacimiento, LocalDate.now());
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
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

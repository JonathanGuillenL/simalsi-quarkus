package org.lab.simalsi.paciente.models;

import jakarta.persistence.*;
import org.lab.simalsi.persona.models.PersonaNatural;
import org.lab.simalsi.solicitud.models.SolicitudEstadoConverter;

import java.time.LocalDate;
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

    public Paciente() {}

    public Paciente(Long id, LocalDate nacimiento, Sexo sexo, PersonaNatural persona) {
        this.id = id;
        this.nacimiento = nacimiento;
        this.sexo = sexo;
        this.persona = persona;
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
}

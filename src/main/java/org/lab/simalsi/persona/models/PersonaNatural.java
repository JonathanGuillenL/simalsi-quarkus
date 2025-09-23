package org.lab.simalsi.persona.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.lab.simalsi.cliente.models.Cliente;

@Entity
public class PersonaNatural extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String apellido;

    @Column(length = 50)
    private String numeroIdentificacion;

    @JsonIgnore
    @OneToOne(mappedBy = "persona")
    private Cliente cliente;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String getFullname() {
        return String.format("%s %s", getNombre(), getApellido());
    }
}

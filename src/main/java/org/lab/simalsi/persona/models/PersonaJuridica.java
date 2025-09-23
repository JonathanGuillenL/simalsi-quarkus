package org.lab.simalsi.persona.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.lab.simalsi.cliente.models.Municipio;

@Entity
public class PersonaJuridica extends Persona {
    private String razonSocial;
    private String RUC;

    @ManyToOne
    private Municipio municipio;

    public PersonaJuridica() {
    }

    public PersonaJuridica(Long id, String nombre, String telefono, String direccion, String razonSocial, String RUC, Municipio municipio) {
        super(id, nombre, telefono, direccion);
        this.razonSocial = razonSocial;
        this.RUC = RUC;
        this.municipio = municipio;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
}

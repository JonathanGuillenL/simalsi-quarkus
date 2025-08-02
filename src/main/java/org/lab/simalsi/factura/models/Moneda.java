package org.lab.simalsi.factura.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Moneda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    private Double tipoCambio;

    private String signoMonetario;

    public Moneda() {}

    public Moneda(String descripcion, Double tipoCambio, String signoMonetario) {
        this.descripcion = descripcion;
        this.tipoCambio = tipoCambio;
        this.signoMonetario = signoMonetario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getSignoMonetario() {
        return signoMonetario;
    }

    public void setSignoMonetario(String signoMonetario) {
        this.signoMonetario = signoMonetario;
    }
}

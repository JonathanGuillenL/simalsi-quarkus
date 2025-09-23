package org.lab.simalsi.factura.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Moneda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @Column(nullable = false, scale = 2)
    private Double tipoCambio;

    private String signoMonetario;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public Moneda() {
        this.createdAt = LocalDateTime.now();
    }

    public Moneda(String descripcion, Double tipoCambio, String signoMonetario, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.descripcion = descripcion;
        this.tipoCambio = tipoCambio;
        this.signoMonetario = signoMonetario;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
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

package org.lab.simalsi.cliente.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String descripcion;

    @ManyToOne
    @JoinColumn(name = "departamento_id", referencedColumnName = "id")
    public Departamento departamento;

    public LocalDateTime createdAt;

    public LocalDateTime deletedAt;

    public Municipio() {}

    public Municipio(Long id, String descripcion, Departamento departamento, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.descripcion = descripcion;
        this.departamento = departamento;
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

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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

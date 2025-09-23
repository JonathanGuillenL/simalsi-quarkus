package org.lab.simalsi.servicio.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ServicioLaboratorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    private Double precio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "procedimiento_id", referencedColumnName = "id")
    private ProcedimientoQuirurgico procedimientoQuirurgico;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public ServicioLaboratorio() {
        this.createdAt = LocalDateTime.now();
    }

    public ServicioLaboratorio(Long id, String descripcion, Double precio, ProcedimientoQuirurgico procedimientoQuirurgico, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.procedimientoQuirurgico = procedimientoQuirurgico;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public ProcedimientoQuirurgico getProcedimientoQuirurgico() {
        return procedimientoQuirurgico;
    }

    public void setProcedimientoQuirurgico(ProcedimientoQuirurgico procedimientoQuirurgico) {
        this.procedimientoQuirurgico = procedimientoQuirurgico;
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

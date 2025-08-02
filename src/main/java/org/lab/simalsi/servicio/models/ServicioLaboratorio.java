package org.lab.simalsi.servicio.models;

import jakarta.persistence.*;

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

    public ServicioLaboratorio() {}

    public ServicioLaboratorio(Long id, String descripcion, Double precio, ProcedimientoQuirurgico procedimientoQuirurgico) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.procedimientoQuirurgico = procedimientoQuirurgico;
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

    public ProcedimientoQuirurgico getProcedimiento() {
        return procedimientoQuirurgico;
    }

    public void setProcedimiento(ProcedimientoQuirurgico procedimientoQuirurgico) {
        this.procedimientoQuirurgico = procedimientoQuirurgico;
    }
}

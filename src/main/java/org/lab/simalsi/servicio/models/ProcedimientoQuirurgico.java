package org.lab.simalsi.servicio.models;

import jakarta.persistence.*;

@Entity
public class ProcedimientoQuirurgico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "region_anatomica_id", nullable = true)
    private RegionAnatomica regionAnatomica;

    public ProcedimientoQuirurgico(){
    }

    public ProcedimientoQuirurgico(Long id, String descripcion){
        this.id = id;
        this.descripcion = descripcion;
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

    public RegionAnatomica getRegionAnatomica() {
        return regionAnatomica;
    }

    public void setRegionAnatomica(RegionAnatomica regionAnatomica) {
        this.regionAnatomica = regionAnatomica;
    }
}

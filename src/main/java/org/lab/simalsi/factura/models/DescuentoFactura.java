package org.lab.simalsi.factura.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class DescuentoFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double porcentaje;

    @Column(nullable = false)
    private LocalDateTime fechaAplica;

    @ManyToOne
    @JoinColumn(name = "descuento_id", referencedColumnName = "id", nullable = false)
    private Descuento descuento;

    public DescuentoFactura() {
    }

    public DescuentoFactura(Long id, Double porcentaje, LocalDateTime fechaAplica, Descuento descuento) {
        this.id = id;
        this.porcentaje = porcentaje;
        this.fechaAplica = fechaAplica;
        this.descuento = descuento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public LocalDateTime getFechaAplica() {
        return fechaAplica;
    }

    public void setFechaAplica(LocalDateTime fechaAplica) {
        this.fechaAplica = fechaAplica;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }
}

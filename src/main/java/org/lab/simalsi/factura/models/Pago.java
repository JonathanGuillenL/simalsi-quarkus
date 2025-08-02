package org.lab.simalsi.factura.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaPago;

    private Double monto;

    private Double tipoCambio;

    private Double cambio;

    private String observaciones;

    private String referencia;

    @ManyToOne
    @JoinColumn(name = "metodo_pago_id", referencedColumnName = "id")
    private MetodoPago metodoPago;

    @ManyToOne
    @JoinColumn(name = "moneda_id", referencedColumnName = "id")
    private Moneda moneda;

    public Pago() {
        this.tipoCambio = null;
        this.cambio = 0D;
        this.fechaPago = LocalDateTime.now();
    }

    public Pago(LocalDateTime fechaPago, Double monto, String observaciones, String referencia,
                MetodoPago metodoPago, Moneda moneda) {
        this.fechaPago = fechaPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.moneda = moneda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;

        this.tipoCambio = this.moneda.getTipoCambio();
    }

    public Double getCambio() {
        return cambio;
    }

    public void setCambio(Double cambio) {
        this.cambio = cambio;
    }

    public Double calcularMontoCambio() {
        return monto * tipoCambio;
    }
}

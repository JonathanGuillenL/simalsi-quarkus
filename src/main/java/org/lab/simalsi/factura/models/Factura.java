package org.lab.simalsi.factura.models;

import jakarta.persistence.*;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.colaborador.models.Colaborador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Colaborador recepcionista;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "factura_id", referencedColumnName = "id", nullable = false)
    private List<DescuentoFactura> descuentos;

    @OneToMany
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    private List<DetalleFactura> detalle;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    private List<Pago> pagos;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double descuento;

    public Factura() {
        this.descuentos = new ArrayList<>();
        this.detalle = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    public Factura(Long id, Cliente cliente, Colaborador recepcionista, List<DescuentoFactura> descuentos, List<DetalleFactura> detalle, List<Pago> pagos) {
        this.id = id;
        this.cliente = cliente;
        this.recepcionista = recepcionista;
        this.descuentos = descuentos;
        this.detalle = detalle;
        this.pagos = pagos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Colaborador getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(Colaborador recepcionista) {
        this.recepcionista = recepcionista;
    }

    public List<DescuentoFactura> getDescuentos() {
        return Collections.unmodifiableList(descuentos);
    }

    public void setDescuentos(List<DescuentoFactura> descuentos) {
        this.descuentos = descuentos;
    }

    public List<DetalleFactura> getDetalle() {
        return Collections.unmodifiableList(detalle);
    }

    public void setDetalle(List<DetalleFactura> detalle) {
        this.detalle = detalle;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public boolean addPago(Pago pago) {
        subtotal = null;
        descuento = null;
        return this.pagos.add(pago);
    }

    public Double calcularDescuento() {
        if (descuento == null) {
            descuento = descuentos.stream()
                .map(DescuentoFactura::getPorcentaje)
                .reduce(0.0D, Double::sum);
            return descuento;
        }

        return descuento;
    }

    public Double calcularSubtotal() {
        if (subtotal == null) {
            subtotal = detalle.stream()
                .map(DetalleFactura::calcularPrecioTotal)
                .reduce(0.0D, Double::sum);
            return subtotal;
        }

        return subtotal;
    }

    public Double getDescuento() {
        return descuento;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Double getTotal() {
        subtotal = subtotal != null? subtotal : calcularSubtotal();
        descuento = descuento != null? descuento : calcularDescuento();
        return subtotal - (subtotal * descuento / 100);
    }

    public boolean isPendiente() {
        Double totalPago = pagos.stream()
            .map(Pago::calcularMontoCambio)
            .reduce(0.0D, Double::sum);
        return getTotal() > totalPago;
    }
}

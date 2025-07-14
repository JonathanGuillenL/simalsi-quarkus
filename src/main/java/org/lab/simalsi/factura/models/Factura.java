package org.lab.simalsi.factura.models;

import jakarta.persistence.*;
import org.lab.simalsi.cliente.models.Cliente;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "factura_id", referencedColumnName = "id", nullable = false)
    private List<DescuentoFactura> descuentos;

    @OneToMany
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    private List<DetalleFactura> detalle;

    public Factura() {
        this.descuentos = new ArrayList<>();
    }

    public Factura(Long id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
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

    public List<DescuentoFactura> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<DescuentoFactura> descuentos) {
        this.descuentos = descuentos;
    }

    public List<DetalleFactura> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleFactura> detalle) {
        this.detalle = detalle;
    }
}

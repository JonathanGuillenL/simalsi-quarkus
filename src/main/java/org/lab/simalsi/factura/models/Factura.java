package org.lab.simalsi.factura.models;

import io.quarkus.logging.Log;
import jakarta.persistence.*;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.common.GeneralErrorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    private String recepcionista;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "factura_id", referencedColumnName = "id", nullable = false)
    private List<DescuentoFactura> descuentos;

    @OneToMany
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    private List<DetalleFactura> detalle;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    private List<Pago> pagos;

    @Column(nullable = false, scale = 2)
    private Double total;

    @Column(nullable = false, scale = 2)
    private Double subtotal;

    @Column(nullable = false, scale = 2)
    private Double descuento;

    @Column(nullable = false)
    private Double saldoPendiente;

    private LocalDateTime createdAt;

    public Factura() {
        this.descuentos = new ArrayList<>();
        this.detalle = new ArrayList<>();
        this.pagos = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public Factura(Long id, Cliente cliente, String recepcionista, List<DescuentoFactura> descuentos, List<DetalleFactura> detalle, List<Pago> pagos, LocalDateTime createdAt) {
        this.id = id;
        this.cliente = cliente;
        this.recepcionista = recepcionista;
        this.descuentos = descuentos;
        this.detalle = detalle;
        this.pagos = pagos;
        this.createdAt = createdAt;
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

    public String getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(String recepcionista) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public boolean addPago(Pago pago) {
        total = null;
        subtotal = null;
        descuento = null;
        saldoPendiente = null;
        return this.pagos.add(pago);
    }

    public void anularPago(Long pagoId) {
        total = null;
        subtotal = null;
        descuento = null;
        saldoPendiente = null;
        Pago pago = getPagos().stream()
            .filter(p -> Objects.equals(p.getId(), pagoId))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Pago no encontrado para el registro de factura."));

        if (ChronoUnit.DAYS.between(LocalDateTime.now(), pago.getFechaPago()) > 1L) {
            throw new GeneralErrorException("El pago no puede ser anulado.");
        }
        pago.anularPago();
    }

    private Double calcularDescuento() {
        if (descuento == null) {
            descuento = descuentos.stream()
                .map(DescuentoFactura::getPorcentaje)
                .reduce(0.0D, Double::sum);
        }

        return descuento;
    }

    private Double calcularSubtotal() {
        if (subtotal == null) {
            subtotal = detalle.stream()
                .map(DetalleFactura::calcularPrecioTotal)
                .reduce(0.0D, Double::sum);
        }

        return subtotal;
    }

    public Double getDescuento() {
        return calcularDescuento();
    }

    public Double getSubtotal() {
        return calcularSubtotal();
    }

    public Double calcularTotales() {
        if (total == null) {
            subtotal = calcularSubtotal();
            descuento = calcularDescuento();
            total = subtotal - new BigDecimal(subtotal * descuento / 100)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        }

        return total;
    }

    public Double getTotal() {
        return calcularTotales();
    }

    public Double getSaldoPendiente() {
        return saldoPendiente;
    }

    public Double calcularSaldoPendiente() {
        if (saldoPendiente == null) {
            Double totalPago = pagos.stream()
                .filter(pago -> pago.getDeletedAt() == null)
                .map(Pago::calcularMontoCambio)
                .reduce(0.0D, Double::sum);

            Log.infof("Calculo de total pagado: %f", totalPago);
            saldoPendiente = new BigDecimal(getTotal() - totalPago)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        }

        return saldoPendiente;
    }
}

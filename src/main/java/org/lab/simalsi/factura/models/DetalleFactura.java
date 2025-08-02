package org.lab.simalsi.factura.models;

import jakarta.persistence.*;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double precio;

    private boolean facturado;

    @ManyToOne
    private ServicioLaboratorio servicioLaboratorio;

    public DetalleFactura() {
    }

    public DetalleFactura(Long id, Double precio, boolean facturado, ServicioLaboratorio servicioLaboratorio) {
        this.id = id;
        this.precio = precio;
        this.facturado = facturado;
        this.servicioLaboratorio = servicioLaboratorio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public boolean isFacturado() {
        return facturado;
    }

    public void setFacturado(boolean facturado) {
        this.facturado = facturado;
    }

    public ServicioLaboratorio getServicioLaboratorio() {
        return servicioLaboratorio;
    }

    public void setServicioLaboratorio(ServicioLaboratorio servicioLaboratorio) {
        this.servicioLaboratorio = servicioLaboratorio;
    }

    public Double calcularPrecioTotal() {
        return precio;
    }
}

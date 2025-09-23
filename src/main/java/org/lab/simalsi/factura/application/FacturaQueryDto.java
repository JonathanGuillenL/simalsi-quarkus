package org.lab.simalsi.factura.application;

import org.jboss.resteasy.reactive.RestQuery;

import java.time.LocalDate;

public class FacturaQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public Long clienteId;

    @RestQuery
    public String clienteNombre;

    @RestQuery
    public LocalDate fechaInicio;

    @RestQuery
    public LocalDate fechaFin;
}

package org.lab.simalsi.factura.application;

import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.ResourceState;

import java.time.LocalDate;

public class DescuentoQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String descripcion;

    @RestQuery
    public LocalDate fecha;

    @RestQuery
    public ResourceState estado;
}

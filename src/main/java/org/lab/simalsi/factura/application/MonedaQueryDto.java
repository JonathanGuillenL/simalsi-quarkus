package org.lab.simalsi.factura.application;

import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.ResourceState;

public class MonedaQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String descripcion;

    @RestQuery
    public ResourceState estado;
}

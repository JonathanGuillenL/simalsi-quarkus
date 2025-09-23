package org.lab.simalsi.servicio.application;

import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.ResourceState;

public class ServicioQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String descripcion;

    @RestQuery
    public String regionAnatomica;

    @RestQuery
    public Long procedimientoId;

    @RestQuery
    public ResourceState estado;
}

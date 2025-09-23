package org.lab.simalsi.servicio.application;

import org.jboss.resteasy.reactive.RestQuery;

public class RegionAnatomicaQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String descripcion;
}

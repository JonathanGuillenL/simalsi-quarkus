package org.lab.simalsi.servicio.application;

import org.jboss.resteasy.reactive.RestQuery;

public class ProcedimientoQuirurgicoQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String descripcion;

    @RestQuery
    public Long regionId;
}

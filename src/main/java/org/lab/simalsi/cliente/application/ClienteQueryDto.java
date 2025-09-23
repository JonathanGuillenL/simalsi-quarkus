package org.lab.simalsi.cliente.application;

import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.cliente.models.TipoCliente;
import org.lab.simalsi.common.ResourceState;

public class ClienteQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String nombres;

    @RestQuery
    public String email;

    @RestQuery
    public String telefono;

    @RestQuery
    public TipoCliente tipoCliente;

    @RestQuery
    public ResourceState estado;
}

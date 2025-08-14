package org.lab.simalsi.cliente.application;

import org.jboss.resteasy.reactive.RestQuery;

public class ClienteQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String nombres;

    @RestQuery
    public String usuario;

    @RestQuery
    public String telefono;
}

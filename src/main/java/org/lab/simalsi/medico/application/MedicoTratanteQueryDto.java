package org.lab.simalsi.medico.application;

import org.jboss.resteasy.reactive.RestQuery;

public class MedicoTratanteQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String nombres;

    @RestQuery
    public String codigoSanitario;

    @RestQuery
    public String telefono;
}

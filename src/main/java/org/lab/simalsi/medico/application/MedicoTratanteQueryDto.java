package org.lab.simalsi.medico.application;

import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.ResourceState;

public class MedicoTratanteQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String nombres;

    @RestQuery
    public String codigoSanitario;

    @RestQuery
    public String telefono;

    @RestQuery
    public ResourceState estado;
}

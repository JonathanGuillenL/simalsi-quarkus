package org.lab.simalsi.colaborador.application;

import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.common.ResourceState;

public class ColaboradorQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String nombres;

    @RestQuery
    public String apellidos;

    @RestQuery
    public String numeroIdentificacion;

    @RestQuery
    public String codigoSanitario;

    @RestQuery
    public String email;

    @RestQuery
    public Long cargoId;

    @RestQuery
    public ResourceState estado;
}

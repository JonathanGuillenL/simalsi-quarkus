package org.lab.simalsi.paciente.application;

import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.paciente.models.Sexo;

public class PacienteQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public String nombres;

    @RestQuery
    public String telefono;

    @RestQuery
    public Sexo sexo;
}

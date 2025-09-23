package org.lab.simalsi.solicitud.application;

import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.solicitud.models.SolicitudEstado;

import java.time.LocalDate;

public class SolicitudCGOQueryDto {
    @RestQuery
    public Long id;

    @RestQuery
    public Long pacienteId;

    @RestQuery
    public Long clienteId;

    @RestQuery
    public Long medicoTratanteId;

    @RestQuery
    public String patologo;

    @RestQuery
    public String histotecnologo;

    @RestQuery
    public Long servicioId;

    @RestQuery
    public String pacienteNombre;

    @RestQuery
    public String medicoNombre;

    @RestQuery
    public String clienteNombre;

    @RestQuery
    public String procedimiento;

    @RestQuery
    public LocalDate fechaInicio;

    @RestQuery
    public LocalDate fechaFin;

    @RestQuery
    public SolicitudEstado solicitudEstado;
}

package org.lab.simalsi.solicitud.application;

import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;
import org.lab.simalsi.solicitud.models.Muestra;
import org.lab.simalsi.solicitud.models.ResultadoCGO;
import org.lab.simalsi.solicitud.models.SolicitudEstado;

import java.time.LocalDateTime;

public record SolicitudCGOResponse(
    Long id,
    Double precio,
    boolean facturado,
    ServicioLaboratorio servicioLaboratorio,
    LocalDateTime fechaSolicitud,
    String observaciones,
    Colaborador recepcionista,
    Cliente cliente,
    Paciente paciente,
    MedicoTratante medicoTratante,
    LocalDateTime fechaTomaMuestra,
    MuestraResponseDto muestra,
    ResultadoCGO resultadoCGO,
    SolicitudEstado estado,
    LocalDateTime deletedAt
) {
}

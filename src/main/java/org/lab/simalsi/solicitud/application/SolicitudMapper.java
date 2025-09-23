package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.paciente.application.PacienteResponsePageDto;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.persona.models.PersonaNatural;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class SolicitudMapper {

    @Inject
    private MuestraMapper muestraMapper;

    public SolicitudCGO toModel(CrearSolicitudCGODto solicitudCGODto) {
        var solicitudCGO = new SolicitudCGO();
        solicitudCGO.setFechaTomaMuestra(solicitudCGODto.fechaTomaMuestra());
        solicitudCGO.setObservaciones(solicitudCGODto.observaciones());

        return solicitudCGO;
    }

    public SolicitudPageResponse toResponsePage(SolicitudCGO solicitudCGO) {
        String medicoTratante = null;
        var personaPaciente = solicitudCGO.getPaciente().getPersona();
        String paciente = personaPaciente.getFullname();
        String servicio = solicitudCGO.getServicioLaboratorio().getDescripcion();
        String cliente = solicitudCGO.getCliente().getPersona().getFullname();
        Double precio = solicitudCGO.getServicioLaboratorio().getPrecio();

        if (solicitudCGO.getMedicoTratante() != null && solicitudCGO.getMedicoTratante().getPersona() != null) {
            var personaMedico = solicitudCGO.getMedicoTratante().getPersona();
            medicoTratante = personaMedico.getFullname();
        }

        return new SolicitudPageResponse(
            solicitudCGO.getId(),
            solicitudCGO.getFechaSolicitud(),
            paciente,
            medicoTratante,
            cliente,
            servicio,
            precio,
            solicitudCGO.getEstado(),
            solicitudCGO.getDeletedAt()
        );
    }

    public SolicitudCGOResponse toResponse(SolicitudCGO solicitudCGO, Colaborador recepcionista, Colaborador histotecnolobo) {
        return new SolicitudCGOResponse(
            solicitudCGO.getId(),
            solicitudCGO.getPrecio(),
            solicitudCGO.isFacturado(),
            solicitudCGO.getServicioLaboratorio(),
            solicitudCGO.getFechaSolicitud(),
            solicitudCGO.getObservaciones(),
            recepcionista,
            solicitudCGO.getCliente(),
            solicitudCGO.getPaciente(),
            solicitudCGO.getMedicoTratante(),
            solicitudCGO.getFechaTomaMuestra(),
            muestraMapper.toResponse(solicitudCGO.getMuestra(), histotecnolobo),
            solicitudCGO.getResultadoCGO(),
            solicitudCGO.getEstado(),
            solicitudCGO.getDeletedAt()
        );
    }
}

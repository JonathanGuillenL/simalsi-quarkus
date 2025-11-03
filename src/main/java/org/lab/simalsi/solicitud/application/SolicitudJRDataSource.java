package org.lab.simalsi.solicitud.application;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.persona.models.PersonaNatural;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SolicitudJRDataSource implements JRDataSource {

    private List<SolicitudCGO> solicitudesCGO;
    private Colaborador recepcionista;
    private int index;

    public SolicitudJRDataSource(List<SolicitudCGO> solicitudesCGO, Colaborador recepcionista) {
        index = -1;
        this.solicitudesCGO = solicitudesCGO;
        this.recepcionista = recepcionista;
    }

    @Override
    public boolean next() throws JRException {
        index++;
        return index < solicitudesCGO.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        String fieldName = jrField.getName();
        SolicitudCGO solicitudCGO = solicitudesCGO.get(index);
        Paciente paciente = solicitudCGO.getPaciente();

        if ("solicitud_id".equals(fieldName)) {
            return solicitudCGO.getId();
        }

        if ("paciente".equals(fieldName)) {
            PersonaNatural persona = paciente.getPersona();
            return persona.getNombre() + " " + persona.getApellido();
        }

        if ("edad".equals(fieldName)) {
            return ChronoUnit.YEARS.between(paciente.getNacimiento(), LocalDate.now());
        }

        if ("sexo".equals(fieldName)) {
            return paciente.getSexo().name();
        }

        if ("observaciones".equals(fieldName)) {
            String observaciones = solicitudCGO.getObservaciones();
            return Objects.requireNonNullElse(observaciones, "-");
        }

        if ("telefono".equals(fieldName)) {
            return paciente.getPersona().getTelefono();
        }

        if ("direccion".equals(fieldName)) {
            return paciente.getPersona().getDireccion();
        }

        if ("region".equals(fieldName)) {
            return solicitudCGO.getServicioLaboratorio().getProcedimientoQuirurgico().getRegionAnatomica().getDescripcion();
        }

        if ("procedimiento".equals(fieldName)) {
            return solicitudCGO.getServicioLaboratorio().getProcedimientoQuirurgico().getDescripcion();
        }

        if ("medico_tratante".equals(fieldName)) {
            MedicoTratante medicoTratante = solicitudCGO.getMedicoTratante();
            if (medicoTratante != null) {
                PersonaNatural persona = medicoTratante.getPersona();
                return persona.getNombre() + " " + persona.getApellido();
            }

            return "-";
        }

        if ("codigo_sanitario".equals(fieldName)) {
            MedicoTratante medicoTratante = solicitudCGO.getMedicoTratante();
            if (medicoTratante != null) {
                return solicitudCGO.getMedicoTratante().getCodigoSanitario();
            }

            return "-";
        }

        if ("fecha_muestra".equals(fieldName)) {
            return Date.from(solicitudCGO.getFechaTomaMuestra().atZone(ZoneId.systemDefault()).toInstant());
        }

        if ("creado_en".equals(fieldName)) {
            return Date.from(solicitudCGO.getFechaSolicitud().atZone(ZoneId.systemDefault()).toInstant());
        }

        if ("recepcionista".equals(fieldName)) {
            return recepcionista.getFullname();
        }

        if ("username".equals(fieldName)) {
            var cliente = solicitudCGO.getCliente();
            return cliente.getUsername();
        }

        if ("numero_ticket".equals(fieldName)) {
            return solicitudCGO.getTicket();
        }

        return null;
    }
}

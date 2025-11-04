package org.lab.simalsi.solicitud.application;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.persona.models.PersonaNatural;
import org.lab.simalsi.solicitud.models.Muestra;
import org.lab.simalsi.solicitud.models.ResultadoCGO;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class ResultadoJRDataSource implements JRDataSource {
    private final List<SolicitudCGO> solicitudesCGO;
    private final Colaborador recepcionista;
    private final Colaborador patologo;
    private int index;

    public ResultadoJRDataSource(List<SolicitudCGO> solicitudesCGO, Colaborador recepcionista, Colaborador patologo) {
        index = -1;
        this.solicitudesCGO = solicitudesCGO;
        this.recepcionista = recepcionista;
        this.patologo = patologo;
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
        Muestra muestra = solicitudCGO.getMuestra();
        ResultadoCGO resultadoCGO = solicitudCGO.getResultadoCGO();

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
            return solicitudCGO.getObservaciones();
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

        if ("creado_en".equals(fieldName)) {
            return Date.from(solicitudCGO.getFechaSolicitud().atZone(ZoneId.systemDefault()).toInstant());
        }

        if ("recepcionista".equals(fieldName)) {
            return recepcionista.getFullname();
        }

        if ("fecha_muestra".equals(fieldName)) {
            return Date.from(solicitudCGO.getFechaTomaMuestra().atZone(ZoneId.systemDefault()).toInstant());
        }

        if ("fecha_ingreso".equals(fieldName)) {
            return Date.from(muestra.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        if ("fecha_procesamiento".equals(fieldName)) {
            return Date.from(muestra.getFechaProcesamiento().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        if ("numero_cortes".equals(fieldName)) {
            return muestra.getNumeroDeCortes();
        }

        if ("peso_muestra".equals(fieldName)) {
            return muestra.getPesoMuestra();
        }

        if ("descripcion_macroscopica".equals(fieldName)) {
            return muestra.getDescripcionMacroscopica();
        }

        if ("diagnostico".equals(fieldName)) {
            return resultadoCGO.getObservaciones();
        }

        if ("patologo".equals(fieldName)) {
            return patologo.getFullname();
        }

        if ("codigo_s_patologo".equals(fieldName)) {
            return patologo.getCodigoSanitario();
        }

        if ("imageUrl".equals(fieldName)) {
            return "";
        }

        return null;
    }
}

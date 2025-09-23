package org.lab.simalsi.factura.application;

import jakarta.enterprise.context.ApplicationScoped;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.persona.models.Persona;
import org.lab.simalsi.persona.models.PersonaNatural;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

import java.util.List;

public class FacturaJRDataSource implements JRDataSource {

    private List<DetalleFactura> detalle;
    private int index;

    public FacturaJRDataSource(List<DetalleFactura> detalle) {
        index = -1;
        this.detalle = detalle;
    }

    @Override
    public boolean next() throws JRException {
        index++;
        return index < detalle.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        String fieldName = jrField.getName();
        DetalleFactura detalleFactura = detalle.get(index);

        if ("solicitud_id".equals(fieldName)) {
            return detalleFactura.getId();
        }

        if ("precio".equals(fieldName)) {
            return detalleFactura.getPrecio();
        }

        if (detalleFactura instanceof SolicitudCGO solicitudCGO) {
            if ("paciente".equals(fieldName)) {
                Paciente paciente = solicitudCGO.getPaciente();
                PersonaNatural persona = paciente.getPersona();
                return persona.getNombre() + " " + persona.getApellido();
            } else if ("servicio".equals(fieldName)) {
                return detalleFactura.getServicioLaboratorio().getDescripcion();
            }
        }
        return null;
    }
}

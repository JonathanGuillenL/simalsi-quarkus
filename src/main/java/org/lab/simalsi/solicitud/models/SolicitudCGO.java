package org.lab.simalsi.solicitud.models;

import jakarta.persistence.*;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;

@Entity
public class SolicitudCGO extends DetalleFactura {
    private String observaciones;

    @ManyToOne
    private Colaborador recepcionista;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private MedicoTratante medicoTratante;

    @OneToOne(fetch = FetchType.LAZY)
    private Muestra muestra;

    @OneToOne(fetch = FetchType.LAZY)
    private ResultadoCGO resultadoCGO;

    @Convert(converter = SolicitudEstadoConverter.class)
    private SolicitudEstado estado;

    public SolicitudCGO() {
    }

    public SolicitudCGO(Long id, Double precio, boolean facturado, String observaciones, Colaborador recepcionista, Cliente cliente,
                        Paciente paciente, MedicoTratante medicoTratante, ServicioLaboratorio servicioLaboratorio, Muestra muestra,
                        ResultadoCGO resultadoCGO, SolicitudEstado estado) {
        super(id, precio, facturado, servicioLaboratorio);
        this.observaciones = observaciones;
        this.recepcionista = recepcionista;
        this.cliente = cliente;
        this.paciente = paciente;
        this.medicoTratante = medicoTratante;
        this.muestra = muestra;
        this.resultadoCGO = resultadoCGO;
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Colaborador getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(Colaborador recepcionista) {
        this.recepcionista = recepcionista;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public MedicoTratante getMedicoTratante() {
        return medicoTratante;
    }

    public void setMedicoTratante(MedicoTratante medicoTratante) {
        this.medicoTratante = medicoTratante;
    }

    public Muestra getMuestra() {
        return muestra;
    }

    public void setMuestra(Muestra muestra) {
        this.muestra = muestra;
    }

    public ResultadoCGO getResultadoCGO() {
        return resultadoCGO;
    }

    public void setResultadoCGO(ResultadoCGO resultadoCGO) {
        this.resultadoCGO = resultadoCGO;
    }

    public SolicitudEstado getEstado() {
        return estado;
    }

    public void setEstado(SolicitudEstado estado) {
        this.estado = estado;
    }

    @Override
    public void setFacturado(boolean facturado) {
        super.setFacturado(facturado);
        if (facturado) {
            this.setEstado(SolicitudEstado.FACTURADO);
        }
    }
}

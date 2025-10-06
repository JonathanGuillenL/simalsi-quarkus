package org.lab.simalsi.solicitud.models;

import jakarta.persistence.*;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;

import java.time.LocalDateTime;

@Entity
public class SolicitudCGO extends DetalleFactura {
    private LocalDateTime fechaSolicitud;

    @Column(length = 500)
    private String observaciones;

    private String recepcionista;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private MedicoTratante medicoTratante;

    @Column(nullable = false)
    private LocalDateTime fechaTomaMuestra;

    @OneToOne(fetch = FetchType.EAGER)
    private Muestra muestra;

    @OneToOne(fetch = FetchType.EAGER)
    private ResultadoCGO resultadoCGO;

    @Convert(converter = SolicitudEstadoConverter.class)
    private SolicitudEstado estado;

    private LocalDateTime deletedAt;

    public SolicitudCGO() {
        this.fechaSolicitud = LocalDateTime.now();
    }

    public SolicitudCGO(Long id, Double precio, boolean facturado, LocalDateTime fechaSolicitud, String observaciones, String recepcionista, Cliente cliente,
                        Paciente paciente, MedicoTratante medicoTratante, LocalDateTime fechaTomaMuestra, ServicioLaboratorio servicioLaboratorio, Muestra muestra,
                        ResultadoCGO resultadoCGO, SolicitudEstado estado) {
        super(id, precio, facturado, servicioLaboratorio);
        this.fechaSolicitud = fechaSolicitud;
        this.observaciones = observaciones;
        this.recepcionista = recepcionista;
        this.cliente = cliente;
        this.paciente = paciente;
        this.medicoTratante = medicoTratante;
        this.fechaTomaMuestra = fechaTomaMuestra;
        this.muestra = muestra;
        this.resultadoCGO = resultadoCGO;
        this.estado = estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(String recepcionista) {
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

    public LocalDateTime getFechaTomaMuestra() {
        return fechaTomaMuestra;
    }

    public void setFechaTomaMuestra(LocalDateTime fechaTomaMuestra) {
        this.fechaTomaMuestra = fechaTomaMuestra;
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}

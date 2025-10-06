package org.lab.simalsi.solicitud.models;

import jakarta.persistence.*;
import org.lab.simalsi.colaborador.models.Colaborador;

import java.util.List;

@Entity
public class ResultadoCGO {
    @Id
    private Long id;

    @Column(length = 500)
    private String observaciones;

    private String patologo;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "resultado_id", referencedColumnName = "id")
    private List<ArchivoAdjunto> archivosAdjuntos;

    @ManyToOne
    private CodigoEnfermedades codigoEnfermedades;

    public ResultadoCGO() {
    }

    public ResultadoCGO(Long id, String observaciones, String patologo, List<ArchivoAdjunto> archivosAdjuntos) {
        this.id = id;
        this.observaciones = observaciones;
        this.patologo = patologo;
        this.archivosAdjuntos = archivosAdjuntos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPatologo() {
        return patologo;
    }

    public void setPatologo(String patologo) {
        this.patologo = patologo;
    }

    public List<ArchivoAdjunto> getArchivosAdjuntos() {
        return archivosAdjuntos;
    }

    public void setArchivosAdjuntos(List<ArchivoAdjunto> archivosAdjuntos) {
        this.archivosAdjuntos = archivosAdjuntos;
    }

    public boolean addArchivoAdjunto(ArchivoAdjunto archivoAdjunto) {
        return archivosAdjuntos.add(archivoAdjunto);
    }

    public CodigoEnfermedades getCodigoEnfermedades() {
        return codigoEnfermedades;
    }

    public void setCodigoEnfermedades(CodigoEnfermedades codigoEnfermedades) {
        this.codigoEnfermedades = codigoEnfermedades;
    }
}

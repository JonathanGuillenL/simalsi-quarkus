package org.lab.simalsi.solicitud.models;

import jakarta.persistence.*;
import org.lab.simalsi.colaborador.models.Colaborador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Muestra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaIngreso;

    private LocalDate fechaProcesamiento;

    private Integer numeroDeCortes;

    private Double pesoMuestra;

    private String descripcionMacroscopica;

    private String histotecnologo;

    @OneToMany
    private List<Lamina> laminas;

    public Muestra() {
    }

    public Muestra(Long id, LocalDate fechaIngreso, LocalDate fechaProcesamiento, Integer numeroDeCortes, Double pesoMuestra, String descripcionMacroscopica, String histotecnologo, List<Lamina> laminas) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
        this.fechaProcesamiento = fechaProcesamiento;
        this.numeroDeCortes = numeroDeCortes;
        this.pesoMuestra = pesoMuestra;
        this.descripcionMacroscopica = descripcionMacroscopica;
        this.histotecnologo = histotecnologo;
        this.laminas = laminas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    public void setFechaProcesamiento(LocalDate fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    public Integer getNumeroDeCortes() {
        return numeroDeCortes;
    }

    public void setNumeroDeCortes(Integer numeroDeCortes) {
        this.numeroDeCortes = numeroDeCortes;
    }

    public Double getPesoMuestra() {
        return pesoMuestra;
    }

    public void setPesoMuestra(Double pesoMuestra) {
        this.pesoMuestra = pesoMuestra;
    }

    public String getDescripcionMacroscopica() {
        return descripcionMacroscopica;
    }

    public void setDescripcionMacroscopica(String descripcionMacroscopica) {
        this.descripcionMacroscopica = descripcionMacroscopica;
    }

    public String getHistotecnologo() {
        return histotecnologo;
    }

    public void setHistotecnologo(String histotecnologo) {
        this.histotecnologo = histotecnologo;
    }

    public List<Lamina> getLaminas() {
        return laminas;
    }

    public void setLaminas(List<Lamina> laminas) {
        this.laminas = laminas;
    }

    public boolean agregarLamina(Lamina lamina) {
        return laminas.add(lamina);
    }
}

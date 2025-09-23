package org.lab.simalsi.solicitud.models;

import jakarta.persistence.*;

@Entity
public class Lamina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer fila;

    private Integer columna;

    @ManyToOne
    @JoinColumn(name = "caja_id")
    private Caja caja;

    public Lamina() {
    }

    public Lamina(Long id, Integer fila, Integer columna) {
        this.id = id;
        this.fila = fila;
        this.columna = columna;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }
}

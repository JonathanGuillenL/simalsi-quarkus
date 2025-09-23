package org.lab.simalsi.solicitud.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroColumnas;

    private Integer numeroFilas;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "caja")
    private List<Lamina> laminas;

    public Caja() {
        laminas = new ArrayList<>();
    }

    public Caja(Long id, Integer numeroColumnas, Integer numeroFilas, List<Lamina> laminas) {
        this.id = id;
        this.numeroColumnas = numeroColumnas;
        this.numeroFilas = numeroFilas;
        this.laminas = laminas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroColumnas() {
        return numeroColumnas;
    }

    public void setNumeroColumnas(Integer numeroColumnas) {
        this.numeroColumnas = numeroColumnas;
    }

    public Integer getNumeroFilas() {
        return numeroFilas;
    }

    public void setNumeroFilas(Integer numeroFilas) {
        this.numeroFilas = numeroFilas;
    }

    public List<Lamina> getLaminas() {
        return laminas;
    }

    public void setLaminas(List<Lamina> laminas) {
        this.laminas = laminas;
    }

    public boolean isDisponible(Integer fila, Integer columna) {
        if (laminas != null) {
            return laminas.stream().noneMatch(lamina -> Objects.equals(lamina.getFila(), fila) &&
                Objects.equals(lamina.getColumna(), columna));
        }
        return false;
    }
}

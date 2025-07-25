package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.solicitud.models.Muestra;

@ApplicationScoped
public class MuestraMapper {

    public Muestra toModel(AgregarMuestraDto muestraDto) {
        var muestra = new Muestra();
        muestra.setFechaProcesamiento(muestraDto.fechaProcesamiento());
        muestra.setNumeroDeCortes(muestraDto.numeroDeCortes());
        muestra.setPesoMuestra(muestraDto.pesoMuestra());
        muestra.setDescripcionMacroscopica(muestraDto.descripcionMacroscopica());

        return muestra;
    }
}

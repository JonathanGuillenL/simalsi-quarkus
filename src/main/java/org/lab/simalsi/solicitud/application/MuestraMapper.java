package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.solicitud.models.Muestra;

@ApplicationScoped
public class MuestraMapper {

    @Inject
    private LaminaMapper laminaMapper;

    public Muestra toModel(AgregarMuestraDto muestraDto) {
        var muestra = new Muestra();
        muestra.setFechaIngreso(muestraDto.fechaIngreso());
        muestra.setFechaProcesamiento(muestraDto.fechaProcesamiento());
        muestra.setNumeroDeCortes(muestraDto.numeroDeCortes());
        muestra.setPesoMuestra(muestraDto.pesoMuestra());
        muestra.setDescripcionMacroscopica(muestraDto.descripcionMacroscopica());

        return muestra;
    }

    public MuestraResponseDto toResponse(Muestra muestra, Colaborador histotecnologo) {
        if (muestra == null) {
            return null;
        }

        return new MuestraResponseDto(
            muestra.getId(),
            muestra.getFechaIngreso(),
            muestra.getFechaProcesamiento(),
            muestra.getNumeroDeCortes(),
            muestra.getPesoMuestra(),
            muestra.getDescripcionMacroscopica(),
            histotecnologo,
            muestra.getLaminas().stream()
                .map(lamina -> new LaminaResponseDto(lamina.getId(), lamina.getFila(), lamina.getColumna(), lamina.getCaja().getId()))
                .toList()
        );
    }
}

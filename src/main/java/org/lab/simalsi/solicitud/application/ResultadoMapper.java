package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.solicitud.models.ResultadoCGO;

@ApplicationScoped
public class ResultadoMapper {

    public ResultadoCGO toModel(CrearResultadoCGODto resultadoCGODto) {
        var resultado = new ResultadoCGO();
        resultado.setObservaciones(resultadoCGODto.diagnostico());

        return resultado;
    }
}

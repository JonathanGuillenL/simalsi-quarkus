package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.solicitud.models.Lamina;

@ApplicationScoped
public class LaminaMapper {

    public Lamina toModel(AgregarLaminaDto laminaDto) {
        var lamina = new Lamina();
        lamina.setFila(laminaDto.fila());
        lamina.setColumna(laminaDto.columna());

        return lamina;
    }
}

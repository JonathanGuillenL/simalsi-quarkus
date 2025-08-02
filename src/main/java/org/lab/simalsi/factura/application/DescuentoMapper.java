package org.lab.simalsi.factura.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.factura.models.Descuento;

@ApplicationScoped
public class DescuentoMapper {

    public Descuento toModel(CrearDescuentoDto descuentoDto) {
        var descuento = new Descuento();
        descuento.setDescripcion(descuentoDto.descripcion());
        descuento.setPorcentaje(descuentoDto.porcentaje());
        descuento.setFechaInicio(descuentoDto.fechaInicio());
        descuento.setFechaFin(descuentoDto.fechaFin());
        descuento.setAnual(descuentoDto.anual());
        descuento.setAutomatico(descuentoDto.automatico());

        return descuento;
    }
}

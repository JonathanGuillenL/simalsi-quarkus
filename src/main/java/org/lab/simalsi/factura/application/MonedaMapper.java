package org.lab.simalsi.factura.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.factura.models.Moneda;

@ApplicationScoped
public class MonedaMapper {

    public Moneda toModel(CrearMonedaDto monedaDto) {
        var moneda = new Moneda();
        moneda.setDescripcion(monedaDto.descripcion());
        moneda.setTipoCambio(monedaDto.tipoCambio());
        moneda.setSignoMonetario(monedaDto.signoMonetario());

        return moneda;
    }
}

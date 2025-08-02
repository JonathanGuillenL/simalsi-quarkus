package org.lab.simalsi.factura.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.factura.models.Pago;

@ApplicationScoped
public class PagoMapper {

    public Pago toModel(PagoDto pagoDto) {
        var pago = new Pago();
        pago.setMonto(pagoDto.monto());
        pago.setObservaciones(pagoDto.observaciones());

        return pago;
    }
}

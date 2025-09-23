package org.lab.simalsi.factura.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.factura.models.Factura;

@ApplicationScoped
public class FacturaMapper {

    FacturaResponseDto toResponse(Factura factura) {
        return new FacturaResponseDto(
            factura.getId(), factura.getCreatedAt(), factura.getCliente(),
            factura.getDescuentos(), factura.getDetalle(),
            factura.getPagos(), factura.getSubtotal(),
            factura.getDescuento(), factura.getTotal(),
            factura.getSaldoPendiente()
        );
    }

    FacturaPageResponseDto toPageResponse(Factura factura) {
        return new FacturaPageResponseDto(
            factura.getId(), factura.getCreatedAt(), factura.getCliente(),
            factura.getSubtotal(), factura.getDescuento(), factura.getTotal(),
            factura.calcularSaldoPendiente(), null
        );
    }
}

package org.lab.simalsi.factura.application;

import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.factura.models.DescuentoFactura;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.factura.models.Pago;

import java.time.LocalDateTime;
import java.util.List;

public record FacturaResponseDto(
    Long id,
    LocalDateTime createdAt,
    Cliente cliente,
    List<DescuentoFactura> descuentos,
    List<DetalleFactura> detalle,
    List<Pago> pagos,
    Double subtotal,
    Double descuento,
    Double total,
    Double saldoPendiente
) {
}

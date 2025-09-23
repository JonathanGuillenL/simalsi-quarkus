package org.lab.simalsi.factura.application;

import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.factura.models.DescuentoFactura;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.factura.models.Pago;
import org.lab.simalsi.solicitud.models.SolicitudEstado;

import java.time.LocalDateTime;
import java.util.List;

public record FacturaPageResponseDto(
    Long id,
    LocalDateTime createdAt,
    Cliente cliente,
    Double subtotal,
    Double descuento,
    Double total,
    Double saldoPendiente,
    SolicitudEstado estado
) {
}

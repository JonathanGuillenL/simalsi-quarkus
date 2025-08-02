package org.lab.simalsi.factura.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.factura.infrastructure.FacturaRepository;
import org.lab.simalsi.factura.infrastructure.MetodoPagoRepository;
import org.lab.simalsi.factura.infrastructure.MonedaRepository;
import org.lab.simalsi.factura.models.Factura;
import org.lab.simalsi.factura.models.MetodoPago;
import org.lab.simalsi.factura.models.Moneda;
import org.lab.simalsi.factura.models.Pago;

@ApplicationScoped
public class PagoService {

    @Inject
    private FacturaRepository facturaRepository;

    @Inject
    private MonedaRepository monedaRepository;

    @Inject
    private MetodoPagoRepository metodoPagoRepository;

    @Inject
    private PagoMapper pagoMapper;
    
    public Pago realizarPago(Long facturaId, PagoDto pagoDto) {
        Factura factura = facturaRepository.findByIdOptional(facturaId)
            .orElseThrow(() -> new NotFoundException("Factura no encontrada."));

        Moneda moneda = monedaRepository.findByIdOptional(pagoDto.monedaId())
            .orElseThrow(() -> new NotFoundException("Tipo de moneda no encontrada."));

        MetodoPago metodoPago = metodoPagoRepository.findByIdOptional(pagoDto.metodoPagoId())
            .orElseThrow(() -> new NotFoundException("Metodo de pago no encontrado."));

        Pago pago = pagoMapper.toModel(pagoDto);
        pago.setCambio(moneda.getTipoCambio());
        pago.setMoneda(moneda);
        pago.setMetodoPago(metodoPago);

        factura.addPago(pago);
        factura.calcularSubtotal();
        factura.calcularDescuento();

        facturaRepository.persist(factura);
        return pago;
    }
}

package org.lab.simalsi.factura.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.GeneralErrorException;
import org.lab.simalsi.factura.infrastructure.FacturaRepository;
import org.lab.simalsi.factura.infrastructure.MetodoPagoRepository;
import org.lab.simalsi.factura.infrastructure.MonedaRepository;
import org.lab.simalsi.factura.infrastructure.PagoRepository;
import org.lab.simalsi.factura.models.Factura;
import org.lab.simalsi.factura.models.MetodoPago;
import org.lab.simalsi.factura.models.Moneda;
import org.lab.simalsi.factura.models.Pago;

import java.util.Objects;

@ApplicationScoped
public class PagoService {

    @Inject
    private FacturaRepository facturaRepository;

    @Inject
    private MonedaRepository monedaRepository;

    @Inject
    private MetodoPagoRepository metodoPagoRepository;

    @Inject
    private PagoRepository pagoRepository;

    @Inject
    private FacturaMapper facturaMapper;

    @Inject
    private PagoMapper pagoMapper;
    
    public Factura realizarPago(Long facturaId, PagoDto pagoDto) {
        Factura factura = facturaRepository.findByIdOptional(facturaId)
            .orElseThrow(() -> new NotFoundException("Factura no encontrada."));

        Moneda moneda = monedaRepository.findByIdOptional(pagoDto.monedaId())
            .orElseThrow(() -> new NotFoundException("Tipo de moneda no encontrada."));

        MetodoPago metodoPago = metodoPagoRepository.findByIdOptional(pagoDto.metodoPagoId())
            .orElseThrow(() -> new NotFoundException("Método de pago no encontrado."));

        Pago pago = pagoMapper.toModel(pagoDto);
        pago.setTipoCambio(moneda.getTipoCambio());
        pago.setMoneda(moneda);
        pago.setMetodoPago(metodoPago);

        if (pago.calcularMontoCambio() > factura.calcularSaldoPendiente()) {
            throw new GeneralErrorException("Monto de pago es mayor al saldo pendiente.");
        }

        factura.addPago(pago);
        factura.calcularTotales();
        factura.calcularSaldoPendiente();

        facturaRepository.persist(factura);
        return factura;
    }

    public FacturaResponseDto anularPago(Long facturaId, Long pagoId) {
        Factura factura = facturaRepository.findByIdOptional(facturaId)
            .orElseThrow(() -> new NotFoundException("Factura no encontrada"));

        factura.anularPago(pagoId);
        factura.calcularTotales();
        factura.calcularSaldoPendiente();

        facturaRepository.persist(factura);

        return facturaMapper.toResponse(factura);
    }
}

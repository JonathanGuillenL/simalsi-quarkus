package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.factura.infrastructure.DetalleFacturaRepository;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.solicitud.infrastructure.ResultadoCGORepository;
import org.lab.simalsi.solicitud.models.ResultadoCGO;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

@ApplicationScoped
public class ResultadoCGOService {

    @Inject
    ResultadoCGORepository resultadoCGORepository;

    @Inject
    DetalleFacturaRepository detalleFacturaRepository;

    @Inject
    ResultadoMapper resultadoMapper;

    public ResultadoCGO crearResultadoCGO(Long solicitudId, CrearResultadoCGODto resultadoCGODto) {
        ResultadoCGO resultadoCGO = resultadoMapper.toModel(resultadoCGODto);

        resultadoCGO.setId(solicitudId);
        resultadoCGORepository.persist(resultadoCGO);

        DetalleFactura detalleFactura = detalleFacturaRepository.findByIdOptional(solicitudId)
            .orElseThrow(() -> new NotFoundException("Solcitud no encontrada"));

        if (detalleFactura instanceof SolicitudCGO solicitudCGO) {
            solicitudCGO.setResultadoCGO(resultadoCGO);
            detalleFacturaRepository.persist(solicitudCGO);
        } else {
            throw new NotFoundException("Solicitud no encontrada");
        }

        return resultadoCGO;
    }
}

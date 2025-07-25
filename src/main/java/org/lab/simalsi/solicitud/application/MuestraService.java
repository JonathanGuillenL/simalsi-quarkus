package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.factura.infrastructure.DetalleFacturaRepository;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.solicitud.infrastructure.LaminaRepository;
import org.lab.simalsi.solicitud.infrastructure.MuestraRepository;
import org.lab.simalsi.solicitud.models.Lamina;
import org.lab.simalsi.solicitud.models.Muestra;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

@ApplicationScoped
public class MuestraService {

    @Inject
    MuestraRepository muestraRepository;

    @Inject
    DetalleFacturaRepository detalleFacturaRepository;

    @Inject
    LaminaRepository laminaRepository;

    @Inject
    MuestraMapper muestraMapper;

    @Inject
    LaminaMapper laminaMapper;

    public Muestra agregarMuestraASolicitud(AgregarMuestraDto muestraDto) {
        Muestra muestra = muestraMapper.toModel(muestraDto);

        muestraRepository.persist(muestra);

        DetalleFactura detalleFactura = detalleFacturaRepository.findByIdOptional(muestraDto.solicitudId())
            .orElseThrow(() -> new NotFoundException("Solcitud no encontrada"));

        if (detalleFactura instanceof SolicitudCGO solicitudCGO) {
            solicitudCGO.setMuestra(muestra);
            detalleFacturaRepository.persist(solicitudCGO);
        } else {
            throw new NotFoundException("Solicitud no encontrada");
        }

        return muestra;
    }

    public Lamina agregarLaminaAMuestra(Long muestraId, AgregarLaminaDto laminaDto) {
        Lamina lamina = laminaMapper.toModel(laminaDto);

        laminaRepository.persist(lamina);

        Muestra muestra = muestraRepository.findByIdOptional(muestraId)
            .orElseThrow(() -> new NotFoundException("Muestra no encontrada"));

        muestra.agregarLamina(lamina);
        muestraRepository.persist(muestra);

        return lamina;
    }
}

package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.factura.infrastructure.DetalleFacturaRepository;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.solicitud.infrastructure.CajaRepository;
import org.lab.simalsi.solicitud.infrastructure.LaminaRepository;
import org.lab.simalsi.solicitud.infrastructure.MuestraRepository;
import org.lab.simalsi.solicitud.infrastructure.SolicitudCGORepository;
import org.lab.simalsi.solicitud.models.Caja;
import org.lab.simalsi.solicitud.models.Lamina;
import org.lab.simalsi.solicitud.models.Muestra;
import org.lab.simalsi.solicitud.models.SolicitudCGO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class MuestraService {

    @Inject
    MuestraRepository muestraRepository;

    @Inject
    SolicitudCGORepository solicitudCGORepository;

    @Inject
    LaminaRepository laminaRepository;

    @Inject
    private CajaRepository cajaRepository;

    @Inject
    MuestraMapper muestraMapper;

    @Inject
    LaminaMapper laminaMapper;

    public Muestra agregarMuestraASolicitud(Long solicitudId, AgregarMuestraDto muestraDto) {
        Muestra muestra = muestraMapper.toModel(muestraDto);
        Map<Long, List<AgregarLaminaDto>> laminasPorCaja = muestraDto.laminas().stream()
            .collect(Collectors.groupingBy(AgregarLaminaDto::cajaId));

        List<Lamina> laminaList = laminasPorCaja.keySet().stream().map(cajaId -> {
            List<Lamina> laminas = laminasPorCaja.get(cajaId).stream()
                .map(laminaMapper::toModel).toList();

            Caja caja = cajaRepository.findByIdOptional(cajaId)
                .orElseThrow(() -> new NotFoundException("Caja no encontrada."));

            caja.addLaminas(laminas);

            cajaRepository.persist(caja);

            return laminas;
        }).flatMap(List::stream).toList();

        muestra.setLaminas(laminaList);
        muestraRepository.persist(muestra);

        SolicitudCGO solicitudCGO = solicitudCGORepository.findByIdOptional(solicitudId)
            .orElseThrow(() -> new NotFoundException("Solcitud no encontrada"));

        solicitudCGO.setMuestra(muestra);
        solicitudCGORepository.persist(solicitudCGO);

        return muestra;
    }
}

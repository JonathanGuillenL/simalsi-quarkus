package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.colaborador.infrastructure.ColaboradorRepository;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.common.GeneralErrorException;
import org.lab.simalsi.solicitud.infrastructure.CajaRepository;
import org.lab.simalsi.solicitud.infrastructure.LaminaRepository;
import org.lab.simalsi.solicitud.infrastructure.MuestraRepository;
import org.lab.simalsi.solicitud.infrastructure.SolicitudCGORepository;
import org.lab.simalsi.solicitud.models.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class MuestraService {

    @Inject
    MuestraRepository muestraRepository;

    @Inject
    SolicitudCGORepository solicitudCGORepository;

    @Inject
    private ColaboradorRepository colaboradorRepository;

    @Inject
    LaminaRepository laminaRepository;

    @Inject
    private CajaRepository cajaRepository;

    @Inject
    MuestraMapper muestraMapper;

    @Inject
    LaminaMapper laminaMapper;

    public MuestraResponseDto agregarMuestraASolicitud(Long solicitudId, String userId, AgregarMuestraDto muestraDto) {
        Muestra muestra = muestraMapper.toModel(muestraDto);

        Colaborador histotecnologo = colaboradorRepository.findColaboradorByUsername(userId)
            .orElseThrow(() -> new NotFoundException("Colaborador no encontrado."));

        Map<Long, List<AgregarLaminaDto>> laminasPorCaja = muestraDto.laminas().stream()
            .collect(Collectors.groupingBy(AgregarLaminaDto::cajaId));

        List<Lamina> laminaList = laminasPorCaja.keySet().stream().map(cajaId -> {
            Caja caja = cajaRepository.findByIdOptional(cajaId)
                .orElseThrow(() -> new NotFoundException("Caja no encontrada."));

            List<Lamina> laminas = laminasPorCaja.get(cajaId).stream()
                .map(laminaDto -> {
                    var lamina = laminaMapper.toModel(laminaDto);
                    lamina.setCaja(caja);
                    return lamina;
                }).toList();

            laminaRepository.persist(laminas);

            return laminas;
        }).flatMap(List::stream).toList();

        muestra.setLaminas(laminaList);
        muestra.setHistotecnologo(userId);
        muestraRepository.persist(muestra);

        SolicitudCGO solicitudCGO = solicitudCGORepository.findByIdOptional(solicitudId)
            .orElseThrow(() -> new NotFoundException("Solicitud no encontrada"));

        solicitudCGO.setMuestra(muestra);
        solicitudCGO.setEstado(SolicitudEstado.PROCESADO);
        solicitudCGORepository.persist(solicitudCGO);

        return muestraMapper.toResponse(muestra, histotecnologo);
    }

    public MuestraResponseDto actualizarMuestraSolicitud(Long solicitudId, String userId, AgregarMuestraDto muestraDto) {
        SolicitudCGO solicitudCGO = solicitudCGORepository.findByIdOptional(solicitudId)
            .orElseThrow(() -> new NotFoundException("Solicitud no encontrada"));

        Colaborador histotecnologo = colaboradorRepository.findColaboradorByUsername(userId)
            .orElseThrow(() -> new NotFoundException("Colaborador no encontrado."));

        Muestra muestra = getMuestra(userId, muestraDto, solicitudCGO);

        muestraRepository.persist(muestra);

        return muestraMapper.toResponse(muestra, histotecnologo);
    }

    public LaminaResponseDto moverLamina(Long id, MoverLaminaDto moverLaminaDto) {
        Lamina lamina = laminaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Lamina no encontrada."));

        Caja caja = cajaRepository.find("SELECT c FROM Caja c LEFT JOIN FETCH c.laminas WHERE c.id=?1", moverLaminaDto.cajaId()).firstResultOptional()
            .orElseThrow(() -> new NotFoundException("Caja no encontrada."));

        if (!caja.isDisponible(moverLaminaDto.fila(), moverLaminaDto.columna())) {
            throw new GeneralErrorException("No hay espacion disponible para la lamina.");
        }

        lamina.setFila(moverLaminaDto.fila());
        lamina.setColumna(moverLaminaDto.columna());
        lamina.setCaja(caja);

        laminaRepository.persist(lamina);

        return new LaminaResponseDto(
            lamina.getId(),
            lamina.getFila(),
            lamina.getColumna(),
            lamina.getCaja().getId()
        );
    }

    private static Muestra getMuestra(String userId, AgregarMuestraDto muestraDto, SolicitudCGO solicitudCGO) {
        Muestra muestra = solicitudCGO.getMuestra();
        if (muestra == null) {
            throw new GeneralErrorException("Solicitud no cuenta con datos de muestra registrados.");
        }

        if (!Objects.equals(solicitudCGO.getEstado(), SolicitudEstado.PROCESADO)) {
            throw new GeneralErrorException("Solicitud no puede ser modificada.");
        }

        muestra.setFechaIngreso(muestraDto.fechaIngreso());
        muestra.setFechaProcesamiento(muestraDto.fechaProcesamiento());
        muestra.setNumeroDeCortes(muestraDto.numeroDeCortes());
        muestra.setPesoMuestra(muestraDto.pesoMuestra());
        muestra.setDescripcionMacroscopica(muestraDto.descripcionMacroscopica());
        muestra.setHistotecnologo(userId);
        return muestra;
    }
}

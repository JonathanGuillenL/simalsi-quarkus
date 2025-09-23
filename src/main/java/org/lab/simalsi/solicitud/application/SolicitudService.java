package org.lab.simalsi.solicitud.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.lab.simalsi.cliente.infrastructure.ClienteRepository;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.colaborador.infrastructure.ColaboradorRepository;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.common.GeneralErrorException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.infrastructure.DetalleFacturaRepository;
import org.lab.simalsi.medico.infrastructure.MedicoTratanteRepository;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.paciente.infrastructure.PacienteRepository;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.servicio.infrastructure.ServicioLaboratorioRepository;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;
import org.lab.simalsi.solicitud.infrastructure.SolicitudCGORepository;
import org.lab.simalsi.solicitud.models.SolicitudCGO;
import org.lab.simalsi.solicitud.models.SolicitudEstado;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class SolicitudService {

    @Inject
    DetalleFacturaRepository detalleFacturaRepository;

    @Inject
    SolicitudCGORepository solicitudCGORepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ColaboradorRepository colaboradorRepository;

    @Inject
    PacienteRepository pacienteRepository;

    @Inject
    MedicoTratanteRepository medicoTratanteRepository;

    @Inject
    ServicioLaboratorioRepository servicioLaboratorioRepository;

    @Inject
    SolicitudMapper solicitudMapper;

    public PageDto<SolicitudPageResponse> obtenerListaSolicitudes(int page, int size, SolicitudCGOQueryDto solicitudCGOQueryDto) {
        PanacheQuery<SolicitudCGO> query = solicitudCGORepository.findByQueryDto(solicitudCGOQueryDto);
        List<SolicitudPageResponse> lista = query.page(Page.of(page, size))
            .stream()
            .map(solicitudMapper::toResponsePage)
            .collect(Collectors.toList());
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public PageDto<SolicitudPageResponse> obtenerListaSolicitudesPorEstado(int page, int size, SolicitudEstado estado) {
        PanacheQuery<SolicitudCGO> query = solicitudCGORepository.findByEstado(estado);
        List<SolicitudPageResponse> lista = query.page(Page.of(page, size))
            .stream()
            .map(solicitudMapper::toResponsePage)
            .collect(Collectors.toList());;
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public PageDto<SolicitudPageResponse> obtenerHistorialSolicitudes(int page, int size, String userId, String rol, SolicitudCGOQueryDto solicitudCGOQueryDto) {
        Log.infof("Rol: %s, User: %s", rol, userId);
        if (!Objects.equals(rol, "ROLE_CLIENTE")) {
            Colaborador colaborador = colaboradorRepository.findColaboradorByUsername(userId)
                .orElseThrow(() -> new NotFoundException("Colaborador no encontrado."));
            Log.infof("Colaborador: %d, Cargo: %s", colaborador.getId(), colaborador.getCargo().getCodigo());

            if (Objects.equals(rol, "ROLE_PATOLOGO")) {
                solicitudCGOQueryDto.patologo = userId;
                solicitudCGOQueryDto.solicitudEstado = SolicitudEstado.FINALIZADO;
            } else if (Objects.equals(rol, "ROLE_HISTOTECNOLOGO")) {
                solicitudCGOQueryDto.histotecnologo = userId;
            }
        } else {
            Cliente cliente = clienteRepository.findByUsername(userId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado."));
            solicitudCGOQueryDto.clienteId = cliente.getId();
        }

        PanacheQuery<SolicitudCGO> query = solicitudCGORepository.findByQueryDto(solicitudCGOQueryDto);
        List<SolicitudPageResponse> lista = query.page(Page.of(page, size))
            .stream()
            .map(solicitudMapper::toResponsePage)
            .collect(Collectors.toList());
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public PageDto<SolicitudPageResponse> obtenerListaSolicitudesNoFacturadasPorClienteId(int page, int size, Long clienteId) {
        PanacheQuery<SolicitudCGO> query = solicitudCGORepository.findByClienteIdAndEstado(clienteId, SolicitudEstado.CREADO);
        List<SolicitudPageResponse> lista = query.page(Page.of(page, size))
            .stream()
            .map(solicitudMapper::toResponsePage)
            .collect(Collectors.toList());
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public SolicitudCGOResponse obtenerSolicitudPorId(Long id) {
        var solicitud = solicitudCGORepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Solicitud no encontrada."));

        Colaborador recepcionista = colaboradorRepository.findColaboradorByUsername(solicitud.getRecepcionista())
            .orElseThrow(() -> new NotFoundException("Recepcionista no encontrado"));

        Colaborador histotecnologo = null;
        if (solicitud.getMuestra() != null) {
            histotecnologo = colaboradorRepository.findColaboradorByUsername(solicitud.getMuestra().getHistotecnologo())
                .orElse(null);
        }

        return solicitudMapper.toResponse(solicitud, recepcionista, histotecnologo);
    }

    public SolicitudCGOResponse registrarSolicitudCGO(String userId, CrearSolicitudCGODto solicitudCGODto) {
        SolicitudCGO solicitudCGO = solicitudMapper.toModel(solicitudCGODto);

        Cliente cliente = clienteRepository.findByIdOptional(solicitudCGODto.clienteId())
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        Colaborador recepcionista = colaboradorRepository.findColaboradorByUsername(userId)
            .orElseThrow(() -> new NotFoundException("Recepcionista no encontrado"));

        Paciente paciente = pacienteRepository.findByIdOptional(solicitudCGODto.pacienteId())
            .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));

        MedicoTratante medicoTratante;

        if (solicitudCGODto.medicoTratanteId() != null) {
            medicoTratante = medicoTratanteRepository.findByIdOptional(solicitudCGODto.medicoTratanteId())
                .orElseThrow(() -> new NotFoundException("Médico tratante no encontrado."));
            solicitudCGO.setMedicoTratante(medicoTratante);
        }

        ServicioLaboratorio servicioLaboratorio = servicioLaboratorioRepository.findByIdOptional(solicitudCGODto.servicioLaboratorioId())
            .orElseThrow(() -> new NotFoundException("Servicio laboratorio no encontrado."));

        solicitudCGO.setCliente(cliente);
        solicitudCGO.setPaciente(paciente);
        solicitudCGO.setRecepcionista(userId);
        solicitudCGO.setServicioLaboratorio(servicioLaboratorio);
        solicitudCGO.setEstado(SolicitudEstado.CREADO);
        solicitudCGO.setPrecio(servicioLaboratorio.getPrecio());
        detalleFacturaRepository.persist(solicitudCGO);

        return solicitudMapper.toResponse(solicitudCGO, recepcionista, null);
    }

    public String generateToken(Long solicitudId, String userId, long amountToAdd, TemporalUnit unit) {
        if (amountToAdd <= 0 || unit == null) {
            throw new IllegalArgumentException();
        }

        Instant now = Instant.now();

        return Jwt.claims()
            .subject(userId)
            .claim("solicitud_id", solicitudId)
            .issuedAt(now)
            .expiresAt(now.plus(amountToAdd, unit))
            .sign();
    }

    public byte[] generarSolicitudPdf(Long solicitudId) {
        HashMap<String, Object> params = new HashMap<>();

        SolicitudCGO solicitudCGO = solicitudCGORepository.findByIdOptional(solicitudId)
            .orElseThrow(() -> new NotFoundException("Solicitud CGO no encontrada."));

        Colaborador recepcionista = colaboradorRepository.findColaboradorByUsername(solicitudCGO.getRecepcionista())
            .orElseThrow(() -> new NotFoundException("Recepcionista no encontrado"));

        SolicitudJRDataSource dataSource = new SolicitudJRDataSource(List.of(solicitudCGO), recepcionista);

        ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("reportes/solicitud_examen.jasper")) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException | IOException e) {
            Log.error(e.getMessage());
            Log.error(e.getCause());
            return null;
        }
    }

    public void desactivarSolicitud(Long id) {
        SolicitudCGO solicitudCGO = solicitudCGORepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Solicitud no encontrada."));

        if (!Objects.equals(solicitudCGO.getEstado(), SolicitudEstado.CREADO)) {
            throw new GeneralErrorException("Solicitud ya ha sido facturada favor desactive la factura asociada.");
        }

        solicitudCGO.setDeletedAt(LocalDateTime.now());
        solicitudCGORepository.persist(solicitudCGO);
    }
}

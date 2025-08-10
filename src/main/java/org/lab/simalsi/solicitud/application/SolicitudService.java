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
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.factura.application.FacturaJRDataSource;
import org.lab.simalsi.factura.infrastructure.DetalleFacturaRepository;
import org.lab.simalsi.factura.models.DetalleFactura;
import org.lab.simalsi.factura.models.Factura;
import org.lab.simalsi.medico.infrastructure.MedicoTratanteRepository;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.paciente.infrastructure.PacienteRepository;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.persona.models.Persona;
import org.lab.simalsi.persona.models.PersonaJuridica;
import org.lab.simalsi.persona.models.PersonaNatural;
import org.lab.simalsi.servicio.infrastructure.ServicioLaboratorioRepository;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;
import org.lab.simalsi.solicitud.infrastructure.SolicitudCGORepository;
import org.lab.simalsi.solicitud.models.SolicitudCGO;
import org.lab.simalsi.solicitud.models.SolicitudEstado;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.List;

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

    public PageDto<SolicitudCGO> obtenerListaSolicitudes(int page, int size) {
        PanacheQuery<SolicitudCGO> query = solicitudCGORepository.findAll();
        List<SolicitudCGO> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public PageDto<SolicitudCGO> obtenerListaSolicitudesPorEstado(int page, int size, SolicitudEstado estado) {
        PanacheQuery<SolicitudCGO> query = solicitudCGORepository.findByEstado(estado);
        List<SolicitudCGO> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public PageDto<SolicitudCGO> obtenerListaSolicitudesNoFacturadasPorClienteId(int page, int size, Long clienteId) {
        PanacheQuery<SolicitudCGO> query = solicitudCGORepository.findByClienteIdAndEstado(clienteId, SolicitudEstado.CREADO);
        List<SolicitudCGO> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public SolicitudCGO obtenerSolicitudPorId(Long id) {
        return solicitudCGORepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Solicitud no encontrada."));
    }

    public SolicitudCGO registrarSolicitudCGO(String userId, CrearSolicitudCGODto solicitudCGODto) {
        SolicitudCGO solicitudCGO = solicitudMapper.toModel(solicitudCGODto);

        Cliente cliente = clienteRepository.findByIdOptional(solicitudCGODto.clienteId())
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        Colaborador recepcionista = colaboradorRepository.findColaboradorByUsername(userId)
            .orElseThrow(() -> new NotFoundException("Recepcionista no encontrado"));

        Paciente paciente = pacienteRepository.findByIdOptional(solicitudCGODto.pacienteId())
            .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));

        MedicoTratante medicoTratante = medicoTratanteRepository.findByIdOptional(solicitudCGODto.medicoTratanteId())
            .orElseThrow(() -> new NotFoundException("Medico tratante no encontrado."));

        ServicioLaboratorio servicioLaboratorio = servicioLaboratorioRepository.findByIdOptional(solicitudCGODto.servicioLaboratorioId())
            .orElseThrow(() -> new NotFoundException("Servicio laboratorio no encontrado."));

        solicitudCGO.setCliente(cliente);
        solicitudCGO.setPaciente(paciente);
        solicitudCGO.setMedicoTratante(medicoTratante);
        solicitudCGO.setRecepcionista(recepcionista);
        solicitudCGO.setServicioLaboratorio(servicioLaboratorio);
        solicitudCGO.setEstado(SolicitudEstado.CREADO);
        detalleFacturaRepository.persist(solicitudCGO);

        return solicitudCGO;
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

        SolicitudJRDataSource dataSource = new SolicitudJRDataSource(List.of(solicitudCGO));

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
}

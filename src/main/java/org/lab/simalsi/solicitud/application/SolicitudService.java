package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.cliente.infrastructure.ClienteRepository;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.colaborador.infrastructure.ColaboradorRepository;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.factura.infrastructure.DetalleFacturaRepository;
import org.lab.simalsi.paciente.infrastructure.PacienteRepository;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.solicitud.models.SolicitudCGO;
import org.lab.simalsi.solicitud.models.SolicitudEstado;

@ApplicationScoped
public class SolicitudService {

    @Inject
    DetalleFacturaRepository detalleFacturaRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ColaboradorRepository colaboradorRepository;

    @Inject
    PacienteRepository pacienteRepository;

    @Inject
    SolicitudMapper solicitudMapper;

    public SolicitudCGO registrarSolicitudCGO(CrearSolicitudCGODto solicitudCGODto) {
        SolicitudCGO solicitudCGO = solicitudMapper.toModel(solicitudCGODto);

        Cliente cliente = clienteRepository.findByIdOptional(solicitudCGODto.clienteId())
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        Colaborador recepcionista = colaboradorRepository.findByIdOptional(solicitudCGODto.recepcionistaId())
            .orElseThrow(() -> new NotFoundException("Recepcionista no encontrado"));

        Paciente paciente = pacienteRepository.findByIdOptional(solicitudCGODto.pacienteId())
            .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));

        solicitudCGO.setCliente(cliente);
        solicitudCGO.setPaciente(paciente);
        solicitudCGO.setRecepcionista(recepcionista);
        solicitudCGO.setEstado(SolicitudEstado.CREADO);
        detalleFacturaRepository.persist(solicitudCGO);

        return solicitudCGO;
    }
}

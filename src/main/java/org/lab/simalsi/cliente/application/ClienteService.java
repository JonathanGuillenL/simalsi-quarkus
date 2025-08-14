package org.lab.simalsi.cliente.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.Response;
import org.lab.simalsi.cliente.infrastructure.ClienteRepository;
import org.lab.simalsi.cliente.models.*;
import org.lab.simalsi.common.CreateKeycloakUserDto;
import org.lab.simalsi.common.KeycloakService;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.paciente.infrastructure.PacienteRepository;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.persona.infrastructure.PersonaMapper;
import org.lab.simalsi.persona.infrastructure.PersonaRepository;
import org.lab.simalsi.persona.models.Persona;
import org.lab.simalsi.persona.models.PersonaJuridica;
import org.lab.simalsi.persona.models.PersonaNatural;

import java.util.List;

@ApplicationScoped
public class ClienteService {

    @Inject
    private KeycloakService keycloakService;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    private PersonaRepository personaRepository;

    @Inject
    PacienteRepository pacienteRepository;

    @Inject
    ClienteMapper clienteMapper;

    @Inject
    private PersonaMapper personaMapper;

    public PageDto<Cliente> obtenerListaClientes(int page, int size, ClienteQueryDto clienteQueryDto) {
        PanacheQuery<Cliente> query = clienteRepository.findByQueryDto(clienteQueryDto);
        List<Cliente> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public Record obtenerClientePorId(Long id) {
        return clienteRepository.findByIdOptional(id)
            .map(cliente -> {
                Persona persona = cliente.getPersona();
                if (persona instanceof PersonaNatural) {
                    return clienteMapper.toResponseNatural(cliente);
                } else {
                    return clienteMapper.toResponseJuridico(cliente);
                }
            })
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado."));
    }

    public Cliente registrarClientePorPacienteId(Long pacienteId, CrearClienteDto clienteDto) {
        Paciente paciente = pacienteRepository.findByIdOptional(pacienteId)
            .orElseThrow(() -> new NotFoundException("Paciente no encontrado."));

        Cliente cliente = clienteMapper.toModel(clienteDto);

        boolean existsEmail = clienteRepository.existsByEmail(clienteDto.email());
        if (existsEmail) {
            throw new RuntimeException("Email ya se encuentra registrado.");
        }

        cliente.setPersona(paciente.getPersona());
        clienteRepository.persist(cliente);

        return cliente;
    }

    public Cliente registrarClienteNatural(CrearClienteNaturalDto clienteDto) {
        Cliente cliente = clienteMapper.toModel(clienteDto);
        PersonaNatural persona = personaMapper.toModel(clienteDto);

        clienteRepository.persist(cliente);
        personaRepository.persist(persona);

        keycloakService.createUser(new CreateKeycloakUserDto(
            persona.getNombre(), persona.getApellido(), cliente.getEmail(), "ROLE_CLIENTE"));

        return cliente;
    }

    public Cliente registrarClienteJuridico(CrearClienteJuridicoDto clienteDto) {
        Cliente cliente = clienteMapper.toModel(clienteDto);
        PersonaJuridica persona = personaMapper.toModel(clienteDto);

        clienteRepository.persist(cliente);
        personaRepository.persist(persona);

        keycloakService.createUser(new CreateKeycloakUserDto(
            persona.getNombre(), "", cliente.getEmail(), "ROLE_CLIENTE"));

        return cliente;
    }
}

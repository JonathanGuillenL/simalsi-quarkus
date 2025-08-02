package org.lab.simalsi.cliente.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Client;
import org.lab.simalsi.cliente.infrastructure.ClienteRepository;
import org.lab.simalsi.cliente.models.*;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.paciente.infrastructure.PacienteRepository;
import org.lab.simalsi.paciente.models.Paciente;

import java.util.List;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    PacienteRepository pacienteRepository;

    @Inject
    ClienteMapper clienteMapper;

    public PageDto<Cliente> obtenerListaClientes(int page, int size) {
        PanacheQuery<Cliente> query = clienteRepository.findAll();
        List<Cliente> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado."));
    }

    public Cliente registrarClientePorPacienteId(Long pacienteId, CrearClienteDto clienteDto) {
        Paciente paciente = pacienteRepository.findByIdOptional(pacienteId)
            .orElseThrow(() -> new NotFoundException("Paciente no encontrado."));

        Cliente cliente = clienteMapper.toModel(clienteDto);

        cliente.setPersona(paciente.getPersona());
        clienteRepository.persist(cliente);

        return cliente;
    }

    public Cliente registrarClienteNatural(CrearClienteNaturalDto clienteDto) {
        Cliente cliente = clienteMapper.toModel(clienteDto);

        clienteRepository.persist(cliente);
        return cliente;
    }

    public Cliente registrarClienteJuridico(CrearClienteJuridicoDto clienteDto) {
        Cliente cliente = clienteMapper.toModel(clienteDto);

        clienteRepository.persist(cliente);
        return cliente;
    }
}

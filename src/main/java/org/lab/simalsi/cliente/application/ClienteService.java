package org.lab.simalsi.cliente.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lab.simalsi.cliente.infrastructure.ClienteRepository;
import org.lab.simalsi.cliente.infrastructure.TipoClienteRepository;
import org.lab.simalsi.cliente.models.*;
import org.lab.simalsi.common.PageDto;

import java.util.List;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    TipoClienteRepository tipoClienteRepository;

    @Inject
    ClienteMapper clienteMapper;

    public PageDto<Cliente> obtenerListaClientes(int page, int size) {
        PanacheQuery<Cliente> query = clienteRepository.findAll();
        List<Cliente> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public ClienteEspontaneo registrarCliente(CrearClienteEspontaneoDto clienteDto) {
        ClienteEspontaneo cliente = clienteMapper.toModel(clienteDto);

        TipoCliente tipoCliente = tipoClienteRepository.findById(clienteDto.tipoId());
        cliente.setTipoCliente(tipoCliente);

        clienteRepository.persist(cliente);
        return cliente;
    }

    public MedicoAfiliado registrarCliente(CrearMedicoAfiliadoDto clienteDto) {
        MedicoAfiliado cliente = clienteMapper.toModel(clienteDto);

        TipoCliente tipoCliente = tipoClienteRepository.findById(clienteDto.tipoId());
        cliente.setTipoCliente(tipoCliente);

        clienteRepository.persist(cliente);
        return cliente;
    }

    public ClinicaAfiliada registrarCliente(CrearClinicaAfiliada clienteDto) {
        ClinicaAfiliada cliente = clienteMapper.toModel(clienteDto);

        TipoCliente tipoCliente = tipoClienteRepository.findById(clienteDto.tipoId());
        cliente.setTipoCliente(tipoCliente);

        clienteRepository.persist(cliente);
        return cliente;
    }
}

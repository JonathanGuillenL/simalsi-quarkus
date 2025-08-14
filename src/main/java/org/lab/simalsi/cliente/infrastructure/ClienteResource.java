package org.lab.simalsi.cliente.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.cliente.application.*;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.common.PageDto;

@Path("/cliente")
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    public PageDto<Cliente> obtenerCliente(@RestQuery @DefaultValue("0") Integer page,
                                           @RestQuery @DefaultValue("10") Integer size,
                                           ClienteQueryDto clienteQueryDto) {
        return clienteService.obtenerListaClientes(page, size, clienteQueryDto);
    }

    @GET
    @Path("{id}")
    public Record obtenerClientePorId(@RestPath Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    @POST
    @Path("paciente/{pacienteId}")
    @Transactional
    public Cliente store(@RestPath Long pacienteId, CrearClienteDto cliente) {
        return clienteService.registrarClientePorPacienteId(pacienteId, cliente);
    }

    @POST
    @Path("natural")
    @Transactional
    public Cliente store(@Valid CrearClienteNaturalDto clienteNaturalDto) {
        return clienteService.registrarClienteNatural(clienteNaturalDto);
    }

    @POST
    @Path("juridico")
    @Transactional
    public Cliente store(@Valid CrearClienteJuridicoDto clienteJuridicoDto) {
        return clienteService.registrarClienteJuridico(clienteJuridicoDto);
    }
}

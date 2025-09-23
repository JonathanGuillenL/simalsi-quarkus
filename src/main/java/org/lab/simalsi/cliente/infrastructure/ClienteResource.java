package org.lab.simalsi.cliente.infrastructure;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.cliente.application.*;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.common.UserDto;

@Path("/cliente")
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public PageDto<Cliente> obtenerCliente(@RestQuery @DefaultValue("0") Integer page,
                                           @RestQuery @DefaultValue("10") Integer size,
                                           ClienteQueryDto clienteQueryDto) {
        return clienteService.obtenerListaClientes(page, size, clienteQueryDto);
    }

    @GET
    @Path("{id}")
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Record obtenerClientePorId(@RestPath Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    @POST
    @Path("paciente/{pacienteId}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Cliente store(@RestPath Long pacienteId, CrearClienteDto cliente) {
        return clienteService.registrarClientePorPacienteId(pacienteId, cliente);
    }

    @POST
    @Path("espontaneo")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public UserDto store(@Valid CrearClienteEspontaneoDto clienteDto) {
        return clienteService.registrarClienteNatural(clienteDto);
    }

    @PUT
    @Path("espontaneo/{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Cliente update(@RestPath Long id, @Valid CrearClienteEspontaneoDto clienteDto) {
        return clienteService.actualizarClienteNatural(id, clienteDto);
    }

    @POST
    @Path("medico")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public UserDto store(@Valid CrearMedicoAfiliado clienteDto) {
        return clienteService.registrarClienteNatural(clienteDto);
    }

    @PUT
    @Path("medico/{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Cliente update(@RestPath Long id, @Valid CrearMedicoAfiliado clienteDto) {
        return clienteService.actualizarClienteNatural(id, clienteDto);
    }

    @POST
    @Path("clinica")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public UserDto store(@Valid CrearClinicaAfiliada clienteJuridicoDto) {
        return clienteService.registrarClienteJuridico(clienteJuridicoDto);
    }

    @PUT
    @Path("clinica/{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN, SimalsiRoles.RECEPCIONISTA})
    public Cliente update(@RestPath Long id, @Valid CrearClinicaAfiliada clienteDto) {
        return clienteService.actualizarClienteJuridico(id, clienteDto);
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void enable(@RestPath Long id) {
        clienteService.activarCliente(id);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @RolesAllowed({SimalsiRoles.ADMIN})
    public void destroy(@RestPath Long id) {
        clienteService.desactivarCliente(id);
    }
}

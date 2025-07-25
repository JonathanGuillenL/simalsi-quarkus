package org.lab.simalsi.cliente.infrastructure;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestQuery;
import org.lab.simalsi.cliente.application.ClienteService;
import org.lab.simalsi.cliente.application.CrearClienteEspontaneoDto;
import org.lab.simalsi.cliente.application.CrearClinicaAfiliada;
import org.lab.simalsi.cliente.application.CrearMedicoAfiliadoDto;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.cliente.models.ClienteEspontaneo;
import org.lab.simalsi.cliente.models.ClinicaAfiliada;
import org.lab.simalsi.cliente.models.MedicoAfiliado;
import org.lab.simalsi.common.PageDto;

@Path("/cliente")
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    public PageDto<Cliente> obtenerCliente(@RestQuery @DefaultValue("0") Integer page,
                                           @RestQuery @DefaultValue("10") Integer size) {
        return clienteService.obtenerListaClientes(page, size);
    }

    @POST
    @Path("_espontaneo")
    @Transactional
    public ClienteEspontaneo store(CrearClienteEspontaneoDto clienteEspontaneoDto) {
        return clienteService.registrarCliente(clienteEspontaneoDto);
    }

    @POST
    @Path("medico-afiliado")
    @Transactional
    public MedicoAfiliado store(CrearMedicoAfiliadoDto medicoAfiliadoDto) {
        return clienteService.registrarCliente(medicoAfiliadoDto);
    }

    @POST
    @Path("/clinica-afiliada")
    @Transactional
    public ClinicaAfiliada store(CrearClinicaAfiliada clinicaAfiliadaDto) {
        return clienteService.registrarCliente(clinicaAfiliadaDto);
    }
}

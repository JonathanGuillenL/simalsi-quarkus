package org.lab.simalsi.cliente.infrastructure;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.lab.simalsi.cliente.application.ClienteService;
import org.lab.simalsi.cliente.application.CrearClienteEspontaneoDto;
import org.lab.simalsi.cliente.models.ClienteEspontaneo;

@Path("/cliente/espontaneo")
public class ClienteEspontaneoResource {

    @Inject
    ClienteService clienteService;


}

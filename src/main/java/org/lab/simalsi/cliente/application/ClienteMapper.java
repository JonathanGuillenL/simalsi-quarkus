package org.lab.simalsi.cliente.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.cliente.models.ClienteEspontaneo;
import org.lab.simalsi.cliente.models.ClinicaAfiliada;
import org.lab.simalsi.cliente.models.MedicoAfiliado;

@ApplicationScoped
public class ClienteMapper {

    public ClienteEspontaneo toModel(CrearClienteEspontaneoDto clienteDto) {
        var cliente = new ClienteEspontaneo();
        cliente.setNombres(clienteDto.nombres());
        cliente.setApellidos(clienteDto.apellidos());
        cliente.setCedula(clienteDto.cedula());
        cliente.setTelefono(clienteDto.telefono());
        cliente.setUsername(clienteDto.username());
        cliente.setEmail(clienteDto.email());

        return cliente;
    }

    public MedicoAfiliado toModel(CrearMedicoAfiliadoDto clienteDto) {
        var cliente = new MedicoAfiliado();
        cliente.setNombres(clienteDto.nombres());
        cliente.setApellidos(clienteDto.apellidos());
        cliente.setCedula(clienteDto.cedula());
        cliente.setTelefono(clienteDto.telefono());
        cliente.setCodigoSanitario(clienteDto.codigoSanitario());
        cliente.setUsername(clienteDto.username());
        cliente.setEmail(clienteDto.email());

        return cliente;
    }

    public ClinicaAfiliada toModel(CrearClinicaAfiliada clienteDto) {
        var cliente = new ClinicaAfiliada();
        cliente.setNombre(clienteDto.nombre());
        cliente.setTelefono(clienteDto.telefono());
        cliente.setDireccion(clienteDto.direccion());
        cliente.setUsername(clienteDto.username());
        cliente.setEmail(clienteDto.email());

        return cliente;
    }
}

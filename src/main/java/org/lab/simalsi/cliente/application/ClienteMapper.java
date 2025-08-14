package org.lab.simalsi.cliente.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.persona.models.PersonaJuridica;
import org.lab.simalsi.persona.models.PersonaNatural;

@ApplicationScoped
public class ClienteMapper {

    public Cliente toModel(CrearClienteDto clienteDto) {
        var cliente = new Cliente();
        cliente.setUsername(clienteDto.username());
        cliente.setEmail(clienteDto.email());

        return cliente;
    }

    public Cliente toModel(CrearClienteNaturalDto clienteDto) {
        var cliente = new Cliente();
        cliente.setEmail(clienteDto.email());

        var persona = new PersonaNatural();
        persona.setNombre(clienteDto.nombres());
        persona.setApellido(clienteDto.apellidos());
        persona.setNumeroIdentificacion(clienteDto.cedula());
        persona.setTelefono(clienteDto.telefono());
        persona.setDireccion(clienteDto.direccion());

        cliente.setPersona(persona);

        return cliente;
    }

    public Cliente toModel(CrearClienteJuridicoDto clienteDto) {
        var cliente = new Cliente();
        cliente.setEmail(clienteDto.email());

        var persona = new PersonaJuridica();
        persona.setNombre(clienteDto.nombre());
        persona.setRazonSocial(clienteDto.razonSocial());
        persona.setTelefono(clienteDto.telefono());
        persona.setDireccion(clienteDto.direccion());

        cliente.setPersona(persona);

        return cliente;
    }

    public ClienteNaturalResponseDto toResponseNatural(Cliente cliente) {
        PersonaNatural persona = (PersonaNatural) cliente.getPersona();
        return new ClienteNaturalResponseDto(
            cliente.getId(), persona.getNombre(), persona.getApellido(),
            persona.getNumeroIdentificacion(), persona.getTelefono(),
            persona.getDireccion(), cliente.getEmail(), cliente.getUsername()
        );
    }

    public ClienteJuridicoResponseDto toResponseJuridico(Cliente cliente) {
        PersonaJuridica personaJuridica = (PersonaJuridica) cliente.getPersona();
        return new ClienteJuridicoResponseDto(
            cliente.getId(), personaJuridica.getNombre(), personaJuridica.getRazonSocial(),
            personaJuridica.getTelefono(), personaJuridica.getDireccion(),
            cliente.getEmail(), cliente.getUsername(), personaJuridica.getRUC()
        );
    }
}

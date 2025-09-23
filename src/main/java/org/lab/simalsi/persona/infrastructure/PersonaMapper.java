package org.lab.simalsi.persona.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.cliente.application.CrearClinicaAfiliada;
import org.lab.simalsi.cliente.application.CrearClienteEspontaneoDto;
import org.lab.simalsi.cliente.application.CrearMedicoAfiliado;
import org.lab.simalsi.persona.models.PersonaJuridica;
import org.lab.simalsi.persona.models.PersonaNatural;

@ApplicationScoped
public class PersonaMapper {

    public PersonaNatural toModel(CrearClienteEspontaneoDto clienteNaturalDto) {
        var persona = new PersonaNatural();
        persona.setNombre(clienteNaturalDto.nombres());
        persona.setApellido(clienteNaturalDto.apellidos());
        persona.setNumeroIdentificacion(clienteNaturalDto.cedula());
        persona.setTelefono(clienteNaturalDto.telefono());
        persona.setDireccion(clienteNaturalDto.direccion());

        return persona;
    }

    public PersonaNatural toModel(CrearMedicoAfiliado clienteNaturalDto) {
        var persona = new PersonaNatural();
        persona.setNombre(clienteNaturalDto.nombres());
        persona.setApellido(clienteNaturalDto.apellidos());
        persona.setNumeroIdentificacion(clienteNaturalDto.cedula());
        persona.setTelefono(clienteNaturalDto.telefono());
        persona.setDireccion(clienteNaturalDto.direccion());

        return persona;
    }

    public PersonaJuridica toModel(CrearClinicaAfiliada clienteJuridicoDto) {
        var persona = new PersonaJuridica();
        persona.setNombre(clienteJuridicoDto.nombre());
        persona.setRazonSocial(clienteJuridicoDto.razonSocial());
        persona.setRUC(clienteJuridicoDto.ruc());
        persona.setTelefono(clienteJuridicoDto.telefono());
        persona.setDireccion(clienteJuridicoDto.direccion());

        return persona;
    }
}

package org.lab.simalsi.cliente.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.paciente.models.Paciente;
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

    public Cliente toModel(CrearClienteEspontaneoDto clienteDto) {
        var cliente = new Cliente();

        if (clienteDto.email() != null && !clienteDto.email().isEmpty()) {
            cliente.setEmail(clienteDto.email());
        }

        return cliente;
    }

    public Cliente toModel(CrearClinicaAfiliada clienteDto) {
        var cliente = new Cliente();

        if (clienteDto.email() != null && !clienteDto.email().isEmpty()) {
            cliente.setEmail(clienteDto.email());
        }

        return cliente;
    }

    public Cliente toModel(CrearMedicoAfiliado clienteDto) {
        var cliente = new Cliente();

        if (clienteDto.email() != null && !clienteDto.email().isEmpty()) {
            cliente.setEmail(clienteDto.email());
        }

        return cliente;
    }

    public ClienteEspontaneoResponseDto toResponseNatural(Cliente cliente, Paciente paciente) {
        PersonaNatural persona = (PersonaNatural) cliente.getPersona();
        if (paciente == null) {
            return new ClienteEspontaneoResponseDto(
                cliente.getId(), persona.getNombre(), persona.getApellido(),
                null, null,
                persona.getNumeroIdentificacion(), persona.getTelefono(),
                persona.getDireccion(), cliente.getEmail(), cliente.getUsername(),
                cliente.getTipoCliente(), null,
                cliente.getCreatedAt(), cliente.getDeletedAt()
            );
        }

        return new ClienteEspontaneoResponseDto(
            cliente.getId(), persona.getNombre(), persona.getApellido(),
            paciente.getNacimiento(), paciente.getSexo(),
            persona.getNumeroIdentificacion(), persona.getTelefono(),
            persona.getDireccion(), cliente.getEmail(), cliente.getUsername(),
            cliente.getTipoCliente(), paciente.getId(),
            cliente.getCreatedAt(), cliente.getDeletedAt()
        );
    }

    public MedicoAfiliadoResponseDto toResponseNatural(Cliente cliente, MedicoTratante medicoTratante) {
        PersonaNatural persona = (PersonaNatural) cliente.getPersona();
        return new MedicoAfiliadoResponseDto(
            cliente.getId(), persona.getNombre(), persona.getApellido(),
            medicoTratante.getCodigoSanitario(),
            persona.getNumeroIdentificacion(), persona.getTelefono(),
            persona.getDireccion(), cliente.getEmail(), cliente.getUsername(),
            medicoTratante.getId(), cliente.getTipoCliente(),
            cliente.getCreatedAt(), cliente.getDeletedAt()
        );
    }

    public ClinicaAfiliadaResponseDto toResponseJuridico(Cliente cliente) {
        PersonaJuridica personaJuridica = (PersonaJuridica) cliente.getPersona();
        return new ClinicaAfiliadaResponseDto(
            cliente.getId(), personaJuridica.getNombre(), personaJuridica.getRazonSocial(),
            personaJuridica.getTelefono(), personaJuridica.getDireccion(),
            cliente.getEmail(), cliente.getUsername(), personaJuridica.getRUC(),
            personaJuridica.getMunicipio().getDepartamento().getId(),
            personaJuridica.getMunicipio().getId(),
            personaJuridica.getId(), cliente.getTipoCliente(),
            cliente.getCreatedAt(), cliente.getDeletedAt()
        );
    }
}

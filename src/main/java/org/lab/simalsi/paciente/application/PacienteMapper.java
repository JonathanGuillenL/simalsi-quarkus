package org.lab.simalsi.paciente.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.persona.models.PersonaNatural;

import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class PacienteMapper {

    public Paciente toModel(CrearPacienteDto pacienteDto) {
        var paciente = new Paciente();
        paciente.setNacimiento(pacienteDto.nacimiento());

        var persona = new PersonaNatural();
        persona.setNombre(pacienteDto.nombres());
        persona.setApellido(pacienteDto.apellidos());
        persona.setTelefono(pacienteDto.telefono());
        paciente.setPersona(persona);
        paciente.setSexo(pacienteDto.sexo());

        return  paciente;
    }

    public PacienteResponsePageDto toResponsePage(Paciente paciente) {
        PersonaNatural persona = paciente.getPersona();
        return new PacienteResponsePageDto(
            paciente.getId(),
            String.format("%s %s", persona.getNombre(), persona.getApellido()),
            StringUtils.capitalize(paciente.getSexo().name()),
            paciente.getEdad(),
            paciente.getNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            persona.getTelefono()
        );
    }

    public PacienteResponseDto toResponse(Paciente paciente) {
        PersonaNatural persona = paciente.getPersona();
        return new PacienteResponseDto(
            paciente.getId(),
            persona.getNombre(),
            persona.getApellido(),
            paciente.getEdad(),
            paciente.getNacimiento(),
            paciente.getSexo(),
            persona.getTelefono(),
            persona.getDireccion()
        );
    }
}

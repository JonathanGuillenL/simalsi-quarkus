package org.lab.simalsi.paciente.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.persona.models.Persona;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.persona.models.PersonaNatural;

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

        return  paciente;
    }
}

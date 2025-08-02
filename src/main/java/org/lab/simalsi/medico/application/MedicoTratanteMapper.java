package org.lab.simalsi.medico.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.persona.models.Persona;
import org.lab.simalsi.persona.models.PersonaNatural;

@ApplicationScoped
public class MedicoTratanteMapper {

    public MedicoTratante toModel(CrearMedicoTratante medicoTratanteDto) {
        var medicoTratante = new MedicoTratante();
        medicoTratante.setCodigoSanitario(medicoTratanteDto.codigoSanitario());

        var persona = new PersonaNatural();
        persona.setNombre(medicoTratanteDto.nombres());
        persona.setApellido(medicoTratanteDto.apellidos());
        persona.setNumeroIdentificacion(medicoTratanteDto.numeroIdentificacion());
        persona.setTelefono(medicoTratanteDto.telefono());
        persona.setDireccion(medicoTratanteDto.direccion());

        medicoTratante.setPersona(persona);

        return medicoTratante;
    }
}

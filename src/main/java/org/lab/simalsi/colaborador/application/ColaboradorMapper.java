package org.lab.simalsi.colaborador.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.colaborador.models.Colaborador;

@ApplicationScoped
public class ColaboradorMapper {

    public Colaborador toModel(CrearColaboradorDto colaboradorDto) {
        var colaborador = new Colaborador();
        colaborador.setNombres(colaboradorDto.nombres());
        colaborador.setApellidos(colaboradorDto.apellidos());
        colaborador.setNumeroIdentificacion(colaboradorDto.numeroIdentificacion());
        colaborador.setTelefono(colaboradorDto.telefono());
        colaborador.setEmail(colaboradorDto.email());

        return colaborador;
    }
}

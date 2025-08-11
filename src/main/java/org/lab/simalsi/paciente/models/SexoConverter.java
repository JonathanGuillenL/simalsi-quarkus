package org.lab.simalsi.paciente.models;

import jakarta.persistence.AttributeConverter;
import jakarta.ws.rs.NotFoundException;

import java.util.stream.Stream;

public class SexoConverter implements AttributeConverter<Sexo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Sexo sexo) {
        if (sexo == null) {
            return null;
        }
        return sexo.ordinal();
    }

    @Override
    public Sexo convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return Stream.of(Sexo.values())
            .filter(s -> s.ordinal() == integer)
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Estado de solicitud no existe."));
    }
}


package org.lab.simalsi.solicitud.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.ws.rs.NotFoundException;

import java.util.stream.Stream;

public class SolicitudEstadoConverter implements AttributeConverter<SolicitudEstado, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SolicitudEstado solicitudEstado) {
        if (solicitudEstado == null) {
            return null;
        }
        return solicitudEstado.ordinal();
    }

    @Override
    public SolicitudEstado convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return Stream.of(SolicitudEstado.values())
            .filter(s -> s.ordinal() == integer)
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Estado de solicitud no existe."));
    }
}

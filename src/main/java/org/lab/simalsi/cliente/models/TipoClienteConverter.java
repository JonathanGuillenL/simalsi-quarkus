package org.lab.simalsi.cliente.models;

import jakarta.persistence.AttributeConverter;
import jakarta.ws.rs.NotFoundException;

import java.util.stream.Stream;

public class TipoClienteConverter implements AttributeConverter<TipoCliente, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoCliente tipoCliente) {
        if (tipoCliente == null) {
            return null;
        }
        return tipoCliente.ordinal();
    }

    @Override
    public TipoCliente convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return Stream.of(TipoCliente.values())
            .filter(s -> s.ordinal() == integer)
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Tipo de cliente no existe."));
    }
}

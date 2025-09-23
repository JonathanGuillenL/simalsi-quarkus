package org.lab.simalsi.solicitud.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lab.simalsi.servicio.models.ProcedimientoQuirurgico;
import org.lab.simalsi.solicitud.infrastructure.CodigoEnfermedadesRepository;
import org.lab.simalsi.solicitud.models.CodigoEnfermedades;

import java.util.List;

@ApplicationScoped
public class CodigoEnfermedadesService {

    @Inject
    private CodigoEnfermedadesRepository codigoEnfermedadesRepository;

    public List<CodigoEnfermedades> obtenerListaCodigoEnfermedades() {
        return codigoEnfermedadesRepository.listAll();
    }
}

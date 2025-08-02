package org.lab.simalsi.servicio.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;

@ApplicationScoped
public class ServicioLaboratorioMapper {

    public ServicioLaboratorio toModel(CrearServicioLaboratorioDto servicioLaboratorioDto) {
        var servicioLaboratorio = new ServicioLaboratorio();
        servicioLaboratorio.setDescripcion(servicioLaboratorioDto.descripcion());
        servicioLaboratorio.setPrecio(servicioLaboratorioDto.precio());

        return servicioLaboratorio;
    }
}

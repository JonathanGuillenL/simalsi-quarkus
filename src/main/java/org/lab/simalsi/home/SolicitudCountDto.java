package org.lab.simalsi.home;

import org.lab.simalsi.solicitud.models.SolicitudEstado;

public class SolicitudCountDto {
    public SolicitudEstado estado;
    public Long cantidad;

    public SolicitudCountDto(SolicitudEstado estado, Long cantidad) {
        this.estado = estado;
        this.cantidad = cantidad;
    }
}

package org.lab.simalsi.home;

import org.lab.simalsi.solicitud.models.SolicitudEstado;

public class SolicitudCountDto {
    public Integer year;
    public Integer month;
    public Long count;

    public SolicitudCountDto(Integer year, Integer month, Long count) {
        this.year = year;
        this.month = month;
        this.count = count;
    }
}

package org.lab.simalsi.home;

import java.util.List;

public class HomeDto {
    public List<SolicitudCountDto> solicitudCount;
    public List<ServicioRank> servicioRank;
    public List<ClinicaAfiliadaByDepartmentCountDto> departamentoClinica;
    public Long totalClientes;
    public Long totalFacturas;
    public Long totalSolicitudes;
    public Long totalServicios;
}

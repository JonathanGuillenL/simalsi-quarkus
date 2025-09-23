package org.lab.simalsi.home;

public class ClinicaAfiliadaByDepartmentCountDto {
    public String departamento;

    public Long count;

    public ClinicaAfiliadaByDepartmentCountDto(String departamento, Long count) {
        this.count = count;
        this.departamento = departamento;
    }
}

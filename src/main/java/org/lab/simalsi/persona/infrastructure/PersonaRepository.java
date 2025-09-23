package org.lab.simalsi.persona.infrastructure;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.lab.simalsi.home.ClinicaAfiliadaByDepartmentCountDto;
import org.lab.simalsi.home.SolicitudCountDto;
import org.lab.simalsi.persona.models.Persona;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class PersonaRepository implements PanacheRepository<Persona> {

    public List<ClinicaAfiliadaByDepartmentCountDto> countDepartamento() {
        return getEntityManager().createQuery("""
            SELECT 
                pj.municipio.departamento.descripcion as departamento,
                COUNT(pj)
            FROM PersonaJuridica pj
            GROUP BY departamento
            ORDER BY departamento
        """, ClinicaAfiliadaByDepartmentCountDto.class)
            .getResultList();
    }
}

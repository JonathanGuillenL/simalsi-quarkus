package org.lab.simalsi.servicio.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.servicio.infrastructure.ProcedimientoQuirurgicoRepository;
import org.lab.simalsi.servicio.infrastructure.ServicioLaboratorioRepository;
import org.lab.simalsi.servicio.models.ProcedimientoQuirurgico;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;

import java.util.List;

@ApplicationScoped
public class ServicioLaboratorioService {

    @Inject
    private ServicioLaboratorioRepository servicioLaboratorioRepository;

    @Inject
    private ProcedimientoQuirurgicoRepository procedimientoQuirurgicoRepository;

    @Inject
    private ServicioLaboratorioMapper servicioLaboratorioMapper;

    public PageDto<ServicioLaboratorio> obtenerPageServicioLaboratorio(int page, int size) {
        PanacheQuery<ServicioLaboratorio> query = servicioLaboratorioRepository.findAll();
        List<ServicioLaboratorio> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public ServicioLaboratorio obtenerServicioLaboratorioPorId(Long id) {
        return servicioLaboratorioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Servicio laboratorio no encontrado."));
    }

    public ServicioLaboratorio registrarServicio(CrearServicioLaboratorioDto servicioLaboratorioDto) {
        ProcedimientoQuirurgico procedimientoQuirurgico = procedimientoQuirurgicoRepository.findByIdOptional(servicioLaboratorioDto.procedimientoId())
            .orElseThrow(() -> new NotFoundException("Procedimiento quirurgico no encontrado."));

        ServicioLaboratorio servicioLaboratorio = servicioLaboratorioMapper.toModel(servicioLaboratorioDto);

        servicioLaboratorio.setProcedimiento(procedimientoQuirurgico);
        servicioLaboratorioRepository.persist(servicioLaboratorio);
        return servicioLaboratorio;
    }
}

package org.lab.simalsi.servicio.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.servicio.infrastructure.ProcedimientoQuirurgicoRepository;
import org.lab.simalsi.servicio.infrastructure.ServicioLaboratorioRepository;
import org.lab.simalsi.servicio.models.ProcedimientoQuirurgico;
import org.lab.simalsi.servicio.models.ServicioLaboratorio;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ServicioLaboratorioService {

    @Inject
    private ServicioLaboratorioRepository servicioLaboratorioRepository;

    @Inject
    private ProcedimientoQuirurgicoRepository procedimientoQuirurgicoRepository;

    @Inject
    private ServicioLaboratorioMapper servicioLaboratorioMapper;

    public PageDto<ServicioLaboratorio> obtenerPageServicioLaboratorio(int page, int size, ServicioQueryDto servicioQueryDto) {
        PanacheQuery<ServicioLaboratorio> query = servicioLaboratorioRepository.findByQueryDto(servicioQueryDto);
        List<ServicioLaboratorio> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public ServicioLaboratorio obtenerServicioLaboratorioPorId(Long id) {
        return servicioLaboratorioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Servicio no encontrado."));
    }

    public ServicioLaboratorio registrarServicio(CrearServicioLaboratorioDto servicioLaboratorioDto) {
        ProcedimientoQuirurgico procedimientoQuirurgico = procedimientoQuirurgicoRepository.findByIdOptional(servicioLaboratorioDto.procedimientoId())
            .orElseThrow(() -> new NotFoundException("Procedimiento quirúrgico no encontrado."));

        ServicioLaboratorio servicioLaboratorio = servicioLaboratorioMapper.toModel(servicioLaboratorioDto);

        servicioLaboratorio.setProcedimientoQuirurgico(procedimientoQuirurgico);
        servicioLaboratorioRepository.persist(servicioLaboratorio);
        return servicioLaboratorio;
    }

    public ServicioLaboratorio actualizarServicio(Long id, CrearServicioLaboratorioDto servicioLaboratorioDto) {
        ServicioLaboratorio servicioLaboratorio = servicioLaboratorioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Servicio no encontrado."));

        ProcedimientoQuirurgico procedimientoQuirurgico = procedimientoQuirurgicoRepository.findByIdOptional(servicioLaboratorioDto.procedimientoId())
            .orElseThrow(() -> new NotFoundException("Procedimiento quirúrgico no encontrado."));

        servicioLaboratorio.setDescripcion(servicioLaboratorioDto.descripcion());
        servicioLaboratorio.setPrecio(servicioLaboratorioDto.precio());
        servicioLaboratorio.setProcedimientoQuirurgico(procedimientoQuirurgico);
        servicioLaboratorioRepository.persist(servicioLaboratorio);
        return servicioLaboratorio;
    }

    public void activarServicio(Long id) {
        ServicioLaboratorio servicioLaboratorio = servicioLaboratorioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Servicio no encontrado"));

        servicioLaboratorio.setDeletedAt(null);
        servicioLaboratorioRepository.persist(servicioLaboratorio);
    }

    public void desactivarServicio(Long id) {
        ServicioLaboratorio servicioLaboratorio = servicioLaboratorioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Servicio no encontrado."));

        servicioLaboratorio.setDeletedAt(LocalDateTime.now());
        servicioLaboratorioRepository.persist(servicioLaboratorio);
    }
}

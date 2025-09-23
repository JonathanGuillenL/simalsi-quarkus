package org.lab.simalsi.servicio.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.DuplicateFieldException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.servicio.infrastructure.ProcedimientoQuirurgicoRepository;
import org.lab.simalsi.servicio.infrastructure.RegionAnatomicaRepository;
import org.lab.simalsi.servicio.models.ProcedimientoQuirurgico;
import org.lab.simalsi.servicio.models.RegionAnatomica;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ProcedimientoQuirurgicoService {

    @Inject
    private ProcedimientoQuirurgicoRepository procedimientoQuirurgicoRepository;

    @Inject
    private RegionAnatomicaRepository regionAnatomicaRepository;

    public PageDto<ProcedimientoQuirurgico> obtenerPageProcedimientoQuirurgico(int page, int size, ProcedimientoQuirurgicoQueryDto procedimientoQuirurgicoQueryDto) {
        PanacheQuery<ProcedimientoQuirurgico> query = procedimientoQuirurgicoRepository.findByQueryDto(procedimientoQuirurgicoQueryDto);
        List<ProcedimientoQuirurgico> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public List<ProcedimientoQuirurgico> obtenerListaProcedimientoPorRegionId(Long regionId) {
        return procedimientoQuirurgicoRepository.findByRegionId(regionId).list();
    }

    public ProcedimientoQuirurgico obtenerProcedimientoQuirurgicoPorId(Long id) {
        return procedimientoQuirurgicoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Procedimiento quirúrgico no encontrado."));
    }

    public ProcedimientoQuirurgico registrarProcedimientoQuirurgico(CrearProcedimientoQuirurgicoDto procedimientoQuirurgicoDto) {
        var procedimientoOptional = procedimientoQuirurgicoRepository.findByDescripcion(procedimientoQuirurgicoDto.descripcion());
        if (procedimientoOptional.isPresent()) {
            throw new DuplicateFieldException("El registro de procedimiento quirúrgico ya existe.");
        }

        RegionAnatomica regionAnatomica = regionAnatomicaRepository.findByIdOptional(procedimientoQuirurgicoDto.regionAnatomicaId())
            .orElseThrow(() -> new NotFoundException("Región anátomica no encontrada."));

        ProcedimientoQuirurgico procedimientoQuirurgico = new ProcedimientoQuirurgico();
        procedimientoQuirurgico.setDescripcion(procedimientoQuirurgicoDto.descripcion().trim());
        procedimientoQuirurgico.setRegionAnatomica(regionAnatomica);

        procedimientoQuirurgicoRepository.persist(procedimientoQuirurgico);

        return procedimientoQuirurgico;
    }

    public ProcedimientoQuirurgico actualizarProcedimientoQuirurgico(Long id, CrearProcedimientoQuirurgicoDto procedimientoQuirurgicoDto) {
        ProcedimientoQuirurgico procedimientoQuirurgico = procedimientoQuirurgicoRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Procedimiento quirúrgico no encontrado."));

        RegionAnatomica regionAnatomica = regionAnatomicaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Región anátomica no encontrada."));

        var procedimientoOptional = procedimientoQuirurgicoRepository.findByDescripcion(procedimientoQuirurgicoDto.descripcion());
        if (procedimientoOptional.isPresent() && !Objects.equals(procedimientoQuirurgico.getId(), procedimientoOptional.get().getId())) {
            throw new DuplicateFieldException("El registro de procedimiento quirúrgico ya existe.");
        }

        procedimientoQuirurgico.setDescripcion(procedimientoQuirurgicoDto.descripcion().trim());
        procedimientoQuirurgico.setRegionAnatomica(regionAnatomica);

        procedimientoQuirurgicoRepository.persist(procedimientoQuirurgico);

        return procedimientoQuirurgico;
    }
}

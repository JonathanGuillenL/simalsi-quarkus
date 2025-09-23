package org.lab.simalsi.servicio.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.common.DuplicateFieldException;
import org.lab.simalsi.common.PageDto;
import org.lab.simalsi.servicio.infrastructure.RegionAnatomicaRepository;
import org.lab.simalsi.servicio.models.RegionAnatomica;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class RegionAnatomicaService {

    @Inject
    private RegionAnatomicaRepository regionAnatomicaRepository;

    public PageDto<RegionAnatomica> obtenerPageRegionAnatomica(int page, int size, RegionAnatomicaQueryDto regionAnatomicaQueryDto) {
        PanacheQuery<RegionAnatomica> query = regionAnatomicaRepository.findByQueryDto(regionAnatomicaQueryDto);
        List<RegionAnatomica> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public List<RegionAnatomica> obtenerListaRegionesAnatomicas() {
        return regionAnatomicaRepository.findAll().list();
    }

    public RegionAnatomica obtenerRegionAnatomicaPorId(Long id) {
        return regionAnatomicaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Región anátomica no encontrada."));
    }

    public RegionAnatomica registrarRegionAnatomica(CrearRegionAnatomicaDto regionAnatomicaDto) {
        var regionOptional = regionAnatomicaRepository.findByDescripcion(regionAnatomicaDto.descripcion());
        if (regionOptional.isPresent()) {
            throw new DuplicateFieldException("El registro de región anatómica ya existe.");
        }

        RegionAnatomica regionAnatomica = new RegionAnatomica();
        regionAnatomica.setDescripcion(regionAnatomicaDto.descripcion().trim());

        regionAnatomicaRepository.persist(regionAnatomica);

        return regionAnatomica;
    }

    public RegionAnatomica actualizarRegionAnatomica(Long id, CrearRegionAnatomicaDto regionAnatomicaDto) {
        RegionAnatomica regionAnatomica = regionAnatomicaRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Región anátomica no encontrada."));

        var regionOptional = regionAnatomicaRepository.findByDescripcion(regionAnatomicaDto.descripcion());
        if (regionOptional.isPresent() && !Objects.equals(regionAnatomica.getId(), regionOptional.get().getId())) {
            throw new DuplicateFieldException("El registro de región anatómica ya existe.");
        }

        regionAnatomica.setDescripcion(regionAnatomicaDto.descripcion().trim());
        regionAnatomicaRepository.persist(regionAnatomica);

        return regionAnatomica;
    }
}

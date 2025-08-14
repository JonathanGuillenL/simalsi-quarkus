package org.lab.simalsi.colaborador.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.lab.simalsi.colaborador.infrastructure.CargoRepository;
import org.lab.simalsi.colaborador.models.Cargo;
import org.lab.simalsi.common.PageDto;

import java.util.List;

@ApplicationScoped
public class CargoService {

    @Inject
    private CargoRepository cargoRepository;

    public PageDto<Cargo> obtenerPageCargo(int page, int size) {
        PanacheQuery<Cargo> query = cargoRepository.findAll();
        List<Cargo> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public List<Cargo> obtenerListaCargo() {
        return cargoRepository.findAll().list();
    }

    public Cargo registrarCargo(CrearCargoDto cargoDto) {
        Cargo cargo = new Cargo();
        cargo.setNombre(cargoDto.nombre());
        cargo.setCodigo(cargoDto.codigo());

        cargoRepository.persist(cargo);

        return cargo;
    }
}

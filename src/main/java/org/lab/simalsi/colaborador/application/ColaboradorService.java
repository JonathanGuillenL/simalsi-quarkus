package org.lab.simalsi.colaborador.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.lab.simalsi.colaborador.infrastructure.CargoRepository;
import org.lab.simalsi.colaborador.infrastructure.ColaboradorRepository;
import org.lab.simalsi.colaborador.models.Cargo;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.common.CreateKeycloakUserDto;
import org.lab.simalsi.common.KeycloakService;
import org.lab.simalsi.common.PageDto;

import java.util.List;

@ApplicationScoped
public class ColaboradorService {

    @Inject
    private KeycloakService keycloakService;

    @Inject
    ColaboradorRepository colaboradorRepository;

    @Inject
    CargoRepository cargoRepository;

    @Inject
    ColaboradorMapper colaboradorMapper;

    public PageDto<Colaborador> obtenerListaColaboradores(int page, int size) {
        PanacheQuery<Colaborador> query = colaboradorRepository.findAll();
        List<Colaborador> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public Colaborador registrarColaborador(CrearColaboradorDto colaboradorDto) {
        Colaborador colaborador = colaboradorMapper.toModel(colaboradorDto);

        Cargo cargo = cargoRepository.findByIdOptional(colaboradorDto.cargoId())
            .orElseThrow(() -> new NotFoundException("Cargo no encontrado"));

        colaborador.setCargo(cargo);
        colaboradorRepository.persist(colaborador);

        if (colaboradorDto.user()) {
            String role = switch (cargo.getCodigo()) {
                case "RECP" -> "ROLE_RECEPCIONISTA";
                case "HIST" -> "ROLE_HISTOTECNOLOGO";
                case "PATO" -> "ROLE_PATOLOGO";
                default -> throw new NotFoundException("Código de cargo no encontrado.");
            };

            keycloakService.createUser(new CreateKeycloakUserDto(
                colaborador.getNombres(), colaborador.getApellidos(), colaborador.getEmail(), role));
        }

        return colaborador;
    }
}

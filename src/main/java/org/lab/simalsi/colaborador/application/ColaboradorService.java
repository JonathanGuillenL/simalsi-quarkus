package org.lab.simalsi.colaborador.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.colaborador.infrastructure.CargoRepository;
import org.lab.simalsi.colaborador.infrastructure.ColaboradorRepository;
import org.lab.simalsi.colaborador.models.Cargo;
import org.lab.simalsi.colaborador.models.Colaborador;
import org.lab.simalsi.common.*;

import java.time.LocalDateTime;
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

    public PageDto<Colaborador> obtenerListaColaboradores(int page, int size, ColaboradorQueryDto colaboradorQueryDto) {
        PanacheQuery<Colaborador> query = colaboradorRepository.findByQueryDto(colaboradorQueryDto);
        List<Colaborador> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public Colaborador obtenerColaboradorPorId(Long id) {
        return colaboradorRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Colaborador no encontrado."));
    }

    public Colaborador registrarColaborador(CrearColaboradorDto colaboradorDto) {
        Colaborador colaborador = colaboradorMapper.toModel(colaboradorDto);

        Cargo cargo = cargoRepository.findByIdOptional(colaboradorDto.cargoId())
            .orElseThrow(() -> new NotFoundException("Cargo no encontrado"));

        if (cargo.isRequiereCodigoSanitario() &&
            (colaboradorDto.codigoSanitario() == null || colaboradorDto.codigoSanitario().isEmpty())) {
            throw new GeneralErrorException("El campo código sanitario es requerido para el cargo seleccionado.");
        }

        colaborador.setCodigoSanitario(colaboradorDto.codigoSanitario());
        colaborador.setCargo(cargo);

        if (colaboradorDto.user()) {
            String role = getRoleByCode(cargo.getCodigo());

            UserDto userDto = keycloakService.createUser(new CreateKeycloakUserDto(
                colaborador.getNombres(), colaborador.getApellidos(), colaborador.getEmail(), role));
            colaborador.setUsername(userDto.username());
        }
        colaboradorRepository.persist(colaborador);

        return colaborador;
    }

    public Colaborador actualizarColaborador(Long id, CrearColaboradorDto colaboradorDto) {
        Colaborador colaborador = colaboradorRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Colaborador no encontrado."));

        Cargo cargo = cargoRepository.findByIdOptional(colaboradorDto.cargoId())
            .orElseThrow(() -> new NotFoundException("Cargo no encontrado"));

        if (cargo.isRequiereCodigoSanitario() &&
            (colaboradorDto.codigoSanitario() == null || colaboradorDto.codigoSanitario().isEmpty())) {
            throw new GeneralErrorException("El campo código sanitario es requerido para el cargo seleccionado.");
        }

        keycloakService.updateEmail(colaborador.getUsername(), colaborador.getNombres(), colaborador.getApellidos(),
            getRoleByCode(cargo.getCodigo()), getRoleByCode(colaborador.getCargo().getCodigo()), colaborador.getEmail());

        colaborador.setCodigoSanitario(colaboradorDto.codigoSanitario());
        colaborador.setCargo(cargo);
        colaborador.setNombres(colaboradorDto.nombres());
        colaborador.setApellidos(colaboradorDto.apellidos());
        colaborador.setNumeroIdentificacion(colaboradorDto.numeroIdentificacion());
        colaborador.setTelefono(colaboradorDto.telefono());
        colaborador.setEmail(colaboradorDto.email());

        colaboradorRepository.persist(colaborador);

        return colaborador;
    }

    public void activarColaborador(Long id) {
        Colaborador colaborador = colaboradorRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Colaborador no encontrado"));

        colaborador.setDeletedAt(null);
        colaboradorRepository.persist(colaborador);
    }

    public void desactivarColaborador(Long id) {
        Colaborador colaborador = colaboradorRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Colaborador no encontrado"));

        colaborador.setDeletedAt(LocalDateTime.now());
        colaboradorRepository.persist(colaborador);
    }

    private String getRoleByCode(String codigo) {
        return switch (codigo) {
            case "RECP" -> SimalsiRoles.RECEPCIONISTA;
            case "HIST" -> SimalsiRoles.HISTOTECNOLOGO;
            case "PATO" -> SimalsiRoles.PATOLOGO;
            case "ADMIN" -> SimalsiRoles.ADMIN;
            default -> throw new NotFoundException("Código de cargo no encontrado.");
        };
    }
}

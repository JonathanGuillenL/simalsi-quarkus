package org.lab.simalsi.cliente.application;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.cliente.infrastructure.ClienteRepository;
import org.lab.simalsi.cliente.infrastructure.MunicipioRepository;
import org.lab.simalsi.cliente.models.*;
import org.lab.simalsi.common.*;
import org.lab.simalsi.medico.infrastructure.MedicoTratanteRepository;
import org.lab.simalsi.medico.models.MedicoTratante;
import org.lab.simalsi.paciente.infrastructure.PacienteRepository;
import org.lab.simalsi.paciente.models.Paciente;
import org.lab.simalsi.persona.infrastructure.PersonaMapper;
import org.lab.simalsi.persona.infrastructure.PersonaRepository;
import org.lab.simalsi.persona.models.PersonaJuridica;
import org.lab.simalsi.persona.models.PersonaNatural;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ClienteService {

    @Inject
    private KeycloakService keycloakService;

    @Inject
    private ClienteRepository clienteRepository;

    @Inject
    private PersonaRepository personaRepository;

    @Inject
    private MedicoTratanteRepository medicoTratanteRepository;

    @Inject
    private PacienteRepository pacienteRepository;

    @Inject
    private ClienteMapper clienteMapper;

    @Inject
    private PersonaMapper personaMapper;

    @Inject
    private MunicipioRepository municipioRepository;

    public PageDto<Cliente> obtenerListaClientes(int page, int size, ClienteQueryDto clienteQueryDto) {
        PanacheQuery<Cliente> query = clienteRepository.findByQueryDto(clienteQueryDto);
        List<Cliente> lista = query.page(Page.of(page, size)).list();
        int totalPages = query.pageCount();

        return new PageDto<>(lista, page, size, totalPages);
    }

    public Record obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado."));

        return switch (cliente.getTipoCliente()) {
            case NONE -> null;
            case CLIENTE_ESPONTANEO -> {
                Paciente paciente = pacienteRepository.findByPersonaId(cliente.getPersona().getId());
                yield clienteMapper.toResponseNatural(cliente, paciente);
            }
            case MEDICO_AFILIADO -> {
                MedicoTratante medicoTratante = medicoTratanteRepository.findByPersonaId(cliente.getPersona().getId())
                    .orElseThrow(() -> new NotFoundException("Paciente no encontrado."));
                yield clienteMapper.toResponseNatural(cliente, medicoTratante);
            }
            case CLINICA_AFILIADA -> clienteMapper.toResponseJuridico(cliente);
        };
    }

    public Cliente registrarClientePorPacienteId(Long pacienteId, CrearClienteDto clienteDto) {
        Paciente paciente = pacienteRepository.findByIdOptional(pacienteId)
            .orElseThrow(() -> new NotFoundException("Paciente no encontrado."));

        Cliente cliente = clienteMapper.toModel(clienteDto);

        boolean existsEmail = clienteRepository.existsByEmail(clienteDto.email());
        if (existsEmail) {
            throw new RuntimeException("Email ya se encuentra registrado.");
        }

        cliente.setPersona(paciente.getPersona());
        clienteRepository.persist(cliente);

        return cliente;
    }

    public UserDto registrarClienteNatural(CrearClienteEspontaneoDto clienteDto) {
        Cliente cliente = clienteMapper.toModel(clienteDto);
        PersonaNatural persona;

        boolean existsEmail = clienteRepository.existsByEmail(clienteDto.email());
        if (existsEmail) {
            throw new RuntimeException("Email ya se encuentra registrado.");
        }

        if (clienteDto.hasPaciente()) {
            Paciente paciente = pacienteRepository.findByIdOptional(clienteDto.pacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente no encontrado."));
            paciente.setNacimiento(clienteDto.nacimiento());
            paciente.setSexo(clienteDto.sexo());

            persona = paciente.getPersona();
            persona.setNombre(clienteDto.nombres());
            persona.setApellido(clienteDto.apellidos());
            persona.setNumeroIdentificacion(clienteDto.cedula());
            persona.setTelefono(clienteDto.telefono());
            persona.setDireccion(clienteDto.direccion());

            pacienteRepository.persist(paciente);
        } else {
            persona = personaMapper.toModel(clienteDto);
            personaRepository.persist(persona);
        }

        UserDto userDto = keycloakService.createUser(new CreateKeycloakUserDto(
            persona.getNombre(), persona.getApellido(), cliente.getEmail(), SimalsiRoles.CLIENTE));

        cliente.setTipoCliente(TipoCliente.CLIENTE_ESPONTANEO);
        cliente.setUsername(userDto.username());
        cliente.setPersona(persona);
        clienteRepository.persist(cliente);

        return userDto;
    }

    public Cliente actualizarClienteNatural(Long id, CrearClienteEspontaneoDto clienteDto) {
        Cliente cliente = clienteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado."));

        if (cliente.getPersona() instanceof PersonaNatural persona) {
            persona.setNombre(clienteDto.nombres());
            persona.setApellido(clienteDto.apellidos());
            persona.setNumeroIdentificacion(clienteDto.cedula());
            persona.setTelefono(clienteDto.telefono());
            persona.setDireccion(clienteDto.direccion());

            Paciente paciente = pacienteRepository.findByPersonaId(persona.getId());
            if (clienteDto.hasPaciente() && paciente == null) {
                paciente = new Paciente();
                paciente.setNacimiento(clienteDto.nacimiento());
                paciente.setSexo(clienteDto.sexo());
                paciente.setPersona(persona);
                pacienteRepository.persist(paciente);
            } else {
                personaRepository.persist(persona);
            }
        }

        return cliente;
    }

    public UserDto registrarClienteNatural(CrearMedicoAfiliado clienteDto) {
        Cliente cliente = clienteMapper.toModel(clienteDto);
        PersonaNatural persona;

        boolean existsEmail = clienteRepository.existsByEmail(clienteDto.email());
        if (existsEmail) {
            throw new RuntimeException("Email ya se encuentra registrado.");
        }
        var existsCodigoSanitario = medicoTratanteRepository.existsByCodigoSanitario(clienteDto.codigoSanitario());

        if (clienteDto.hasMedicoTratante()) {
            MedicoTratante medicoTratante = medicoTratanteRepository.findByIdOptional(clienteDto.medicoTratanteId())
                .orElseThrow(() -> new NotFoundException("Médico tratante no encontrado."));
            if (existsCodigoSanitario.isPresent() && !Objects.equals(medicoTratante.getId(), existsCodigoSanitario.get().getId())) {
                throw new DuplicateFieldException("Código sanitario ya ha sido registrado.");
            }

            medicoTratante.setCodigoSanitario(clienteDto.codigoSanitario());

            persona = medicoTratante.getPersona();
            persona.setNombre(clienteDto.nombres());
            persona.setApellido(clienteDto.apellidos());
            persona.setNumeroIdentificacion(clienteDto.cedula());
            persona.setTelefono(clienteDto.telefono());
            persona.setDireccion(clienteDto.direccion());

            medicoTratanteRepository.persist(medicoTratante);
        } else {
            MedicoTratante medicoTratante = new MedicoTratante();
            persona = personaMapper.toModel(clienteDto);

            if (existsCodigoSanitario.isPresent()) {
                throw new DuplicateFieldException("Código sanitario ya ha sido registrado.");
            }

            medicoTratante.setCodigoSanitario(clienteDto.codigoSanitario());
            medicoTratante.setPersona(persona);
            medicoTratanteRepository.persist(medicoTratante);
        }

        UserDto userDto = keycloakService.createUser(new CreateKeycloakUserDto(
            persona.getNombre(), persona.getApellido(), cliente.getEmail(), "ROLE_CLIENTE"));

        cliente.setTipoCliente(TipoCliente.MEDICO_AFILIADO);
        cliente.setUsername(userDto.username());
        cliente.setPersona(persona);
        clienteRepository.persist(cliente);

        return userDto;
    }

    public Cliente actualizarClienteNatural(Long id, CrearMedicoAfiliado clienteDto) {
        Cliente cliente = clienteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado."));

        if (cliente.getPersona() instanceof PersonaNatural persona) {
            MedicoTratante medicoTratante = medicoTratanteRepository.findByIdOptional(clienteDto.medicoTratanteId())
                .orElseThrow(() -> new NotFoundException("Médico tratante no encontrado."));

            var existsCodigoSanitario = medicoTratanteRepository.existsByCodigoSanitario(clienteDto.codigoSanitario());
            if (existsCodigoSanitario.isPresent() && !Objects.equals(medicoTratante.getId(), existsCodigoSanitario.get().getId())) {
                throw new DuplicateFieldException("Código sanitario ya ha sido registrado.");
            }

            medicoTratante.setCodigoSanitario(clienteDto.codigoSanitario());

            persona = medicoTratante.getPersona();
            persona.setNombre(clienteDto.nombres());
            persona.setApellido(clienteDto.apellidos());
            persona.setNumeroIdentificacion(clienteDto.cedula());
            persona.setTelefono(clienteDto.telefono());
            persona.setDireccion(clienteDto.direccion());

            medicoTratanteRepository.persist(medicoTratante);
        }

        return cliente;
    }

    public UserDto registrarClienteJuridico(CrearClinicaAfiliada clienteDto) {
        Municipio municipio = municipioRepository.findByIdOptional(clienteDto.municipioId())
            .orElseThrow(() -> new NotFoundException("Municipio no encontrado"));

        Cliente cliente = clienteMapper.toModel(clienteDto);
        PersonaJuridica persona = personaMapper.toModel(clienteDto);
        persona.setMunicipio(municipio);

        boolean existsEmail = clienteRepository.existsByEmail(clienteDto.email());
        if (existsEmail) {
            throw new RuntimeException("Email ya se encuentra registrado.");
        }

        personaRepository.persist(persona);

        UserDto userDto = keycloakService.createUser(new CreateKeycloakUserDto(
            persona.getNombre(), "", cliente.getEmail(), "ROLE_CLIENTE"));

        cliente.setTipoCliente(TipoCliente.CLINICA_AFILIADA);
        cliente.setUsername(userDto.username());
        cliente.setPersona(persona);
        clienteRepository.persist(cliente);

        return userDto;
    }

    public Cliente actualizarClienteJuridico(Long id, CrearClinicaAfiliada clienteDto) {
        Cliente cliente = clienteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado."));

        if (cliente.getPersona() instanceof PersonaJuridica persona) {
            Municipio municipio = municipioRepository.findByIdOptional(clienteDto.municipioId())
                .orElseThrow(() -> new NotFoundException("Municipio no encontrado"));

            persona.setNombre(clienteDto.nombre());
            persona.setRazonSocial(clienteDto.razonSocial());
            persona.setRUC(clienteDto.ruc());
            persona.setMunicipio(municipio);
            persona.setTelefono(clienteDto.telefono());
            persona.setDireccion(clienteDto.direccion());

            personaRepository.persist(persona);
        }

        return cliente;
    }

    public void activarCliente(Long id) {
        Cliente cliente = clienteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        cliente.setDeletedAt(null);
        clienteRepository.persist(cliente);
    }

    public void desactivarCliente(Long id) {
        Cliente cliente = clienteRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        cliente.setDeletedAt(LocalDateTime.now());
        clienteRepository.persist(cliente);
    }
}

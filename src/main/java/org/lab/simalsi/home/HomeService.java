package org.lab.simalsi.home;

import io.quarkus.cache.CacheResult;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.lab.simalsi.cliente.infrastructure.ClienteRepository;
import org.lab.simalsi.cliente.models.Cliente;
import org.lab.simalsi.common.SimalsiRoles;
import org.lab.simalsi.factura.infrastructure.FacturaRepository;
import org.lab.simalsi.persona.infrastructure.PersonaRepository;
import org.lab.simalsi.servicio.infrastructure.ServicioLaboratorioRepository;
import org.lab.simalsi.solicitud.infrastructure.SolicitudCGORepository;

import java.util.List;

@ApplicationScoped
public class HomeService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    FacturaRepository facturaRepository;

    @Inject
    SolicitudCGORepository solicitudCGORepository;

    @Inject
    ServicioLaboratorioRepository servicioLaboratorioRepository;

    @Inject
    PersonaRepository personaRepository;

    @CacheResult(cacheName = "home-data")
    public HomeDto getHomeDataByUserId(String userId, List<String> userRoles) {
        HomeDto homeDto = null;

        if (userRoles.contains(SimalsiRoles.ADMIN)) {
            homeDto = getHomeDataAdmin();
        } else if (userRoles.contains(SimalsiRoles.RECEPCIONISTA)) {
            homeDto = getHomeDataRecepcionista(userId);
        } else if (userRoles.contains(SimalsiRoles.CLIENTE)) {
            homeDto = getHomeDataCliente(userId);
        }
        return homeDto;
    }

    private HomeDto getHomeDataAdmin() {
        var homeDto = new HomeDto();
        homeDto.solicitudCount = solicitudCGORepository.countLast12Month();
        homeDto.servicioRank = solicitudCGORepository.solicitudesRankedByServicio();
        homeDto.departamentoClinica = personaRepository.countDepartamento();
        homeDto.totalSolicitudes = solicitudCGORepository.count();
        homeDto.totalClientes = clienteRepository.count();
        homeDto.totalFacturas = facturaRepository.count();
        homeDto.totalServicios = servicioLaboratorioRepository.count();
        return homeDto;
    }

    private HomeDto getHomeDataRecepcionista(String userId) {
        var homeDto = new HomeDto();
        homeDto.solicitudCount = solicitudCGORepository.countLast12MonthByRecepcionista(userId);
        homeDto.servicioRank = solicitudCGORepository.solicitudesRankedByServicio();
        homeDto.departamentoClinica = personaRepository.countDepartamento();
        homeDto.totalSolicitudes = solicitudCGORepository.count("recepcionista", userId);
        homeDto.totalClientes = clienteRepository.count();
        homeDto.totalFacturas = facturaRepository.count("recepcionista", userId);
        homeDto.totalServicios = servicioLaboratorioRepository.count();
        return homeDto;
    }

    private HomeDto getHomeDataCliente(String userId) {
        var homeDto = new HomeDto();
        Cliente cliente = clienteRepository.findByUsername(userId)
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado."));

        homeDto.solicitudCount = solicitudCGORepository.countLast12MonthByCliente(cliente.getId());
        homeDto.servicioRank = solicitudCGORepository.solicitudesRankedByServicioByClienteId(cliente.getId());
        homeDto.departamentoClinica = personaRepository.countDepartamento();
        homeDto.totalSolicitudes = solicitudCGORepository.count("cliente.id", cliente.getId());
        homeDto.totalClientes = clienteRepository.count();
        homeDto.totalFacturas = facturaRepository.count("cliente.id", cliente.getId());
        homeDto.totalServicios = servicioLaboratorioRepository.count();
        return homeDto;
    }
}

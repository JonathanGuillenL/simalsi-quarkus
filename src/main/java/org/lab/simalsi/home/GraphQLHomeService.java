package org.lab.simalsi.home;

import io.quarkus.cache.CacheResult;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.lab.simalsi.solicitud.infrastructure.SolicitudCGORepository;

@GraphQLApi
public class GraphQLHomeService {

    @Inject
    private SolicitudCGORepository solicitudCGORepository;

    @Query
    @CacheResult(cacheName = "solicitudCount")
    public HomeDto count() {
        var homeDto = new HomeDto();
        homeDto.solicitudCount = solicitudCGORepository.countSolicitudByEstado();
        return homeDto;
    }
}

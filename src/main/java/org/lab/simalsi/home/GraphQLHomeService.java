package org.lab.simalsi.home;

import io.quarkus.cache.CacheResult;
import io.quarkus.logging.Log;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.lab.simalsi.solicitud.infrastructure.SolicitudCGORepository;

import java.util.List;

@GraphQLApi
public class GraphQLHomeService {

    @Inject
    private HomeService homeService;

    @Inject
    private SecurityIdentity securityIdentity;

    @Query
    public HomeDto count() {
        if (securityIdentity == null || securityIdentity.getPrincipal() == null) {
            return new HomeDto();
        }
        String userId = securityIdentity.getPrincipal().getName();
        List<String> roles = securityIdentity.getRoles().stream().toList();
        Log.infof("Hola: %s, Roles: %s", userId, roles);

        return homeService.getHomeDataByUserId(userId, roles);
    }
}

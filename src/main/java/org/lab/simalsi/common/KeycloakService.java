package org.lab.simalsi.common;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class KeycloakService {
    private final String REALM = "mi-realm-dev";

    @Inject
    private Keycloak keycloak;

    private String generarUsername(CreateKeycloakUserDto keycloakUserDto) {
        StringBuilder builder = new StringBuilder();

        builder.append(keycloakUserDto.firstName().toLowerCase().charAt(0));

        int espacioIndex = keycloakUserDto.lastName().indexOf(' ');

        if (espacioIndex != -1) {
            builder.append(keycloakUserDto.lastName().substring(0, espacioIndex));
        } else {
            builder.append(keycloakUserDto.lastName());
        }

        Random random = new Random();
        int randNumber = random.nextInt(9999);
        builder.append(String.format("%04d", randNumber));

        return builder.toString();
    }

    public String createUser(CreateKeycloakUserDto keycloakUserDto) {
        RealmResource realmResource = keycloak.realm(REALM);
        UsersResource usersResource = realmResource.users();
        String username;
        boolean existsUsername;

        do {
            username = generarUsername(keycloakUserDto);
            existsUsername = !usersResource.searchByUsername(username, true).isEmpty();
        } while (existsUsername);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setFirstName(keycloakUserDto.firstName());
        user.setLastName(keycloakUserDto.lastName());
        user.setEmail(keycloakUserDto.email());
        user.setEnabled(true);

        Response response = keycloak.realm(REALM).users().create(user);
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = usersResource.get(userId);

        RoleRepresentation role = realmResource.roles().get(keycloakUserDto.role()).toRepresentation();

        userResource.roles().realmLevel().add(Collections.singletonList(role));

        return username;
    }

    public List<RoleRepresentation> getRoles() {
        return keycloak.realm(REALM).roles().list();
    }
}

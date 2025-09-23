package org.lab.simalsi.common;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class KeycloakService {
    private final String REALM = "SIMALSI";

    @Inject
    private Keycloak keycloak;

    private String generarUsername(CreateKeycloakUserDto keycloakUserDto) {
        StringBuilder builder = new StringBuilder();

        if (keycloakUserDto.lastName() != null && !keycloakUserDto.lastName().isEmpty()) {
            String apellido = keycloakUserDto.lastName().toLowerCase();
            builder.append(keycloakUserDto.firstName().toLowerCase().charAt(0));

            int espacioIndex = apellido.indexOf(' ');

            if (espacioIndex != -1) {
                builder.append(apellido.replaceAll("\\s+", ""), 0, espacioIndex);
            } else {
                builder.append(apellido.replaceAll("\\s+", ""));
            }
        } else {
            builder.append(keycloakUserDto.firstName().toLowerCase().replaceAll("\\s+", ""));
        }

        Random random = new Random();
        int randNumber = random.nextInt(9999);
        builder.append(String.format("%04d", randNumber));

        return builder.toString();
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public UserDto createUser(CreateKeycloakUserDto keycloakUserDto) {
        RealmResource realmResource = keycloak.realm(REALM);
        UsersResource usersResource = realmResource.users();
        String username;
        boolean existsUsername;

        do {
            username = generarUsername(keycloakUserDto);
            existsUsername = !usersResource.searchByUsername(username, true).isEmpty();
        } while (existsUsername);

        String temporaryPassword = generateRandomPassword(8);

        UserDto userDto = new UserDto(username, temporaryPassword);
        UserRepresentation user = getUserRepresentation(keycloakUserDto, userDto);

        Response response = keycloak.realm(REALM).users().create(user);
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = usersResource.get(userId);

        if (keycloakUserDto.email() != null && !keycloakUserDto.email().isEmpty()) {
            userResource.executeActionsEmail("frontend-spa", "http://localhost:5173/", List.of("UPDATE_PASSWORD"));
        }

        RoleRepresentation role = realmResource.roles().get(keycloakUserDto.role()).toRepresentation();

        userResource.roles().realmLevel().add(Collections.singletonList(role));

        return userDto;
    }

    public void updateEmail(String username, String firstName, String lastName, String role, String deleteRole, String newEmail) {
        RealmResource realmResource = keycloak.realm(REALM);
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> users = realmResource.users().searchByUsername(username, true);

        if (users.isEmpty()) {
            return;
        }

        UserRepresentation user = users.getFirst();
        UserResource userResource = usersResource.get(user.getId());

        user.setEmail(newEmail);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailVerified(false);

        RoleRepresentation realmDeleteRole = realmResource.roles().get(deleteRole).toRepresentation();
        userResource.roles().realmLevel().remove(List.of(realmDeleteRole));

        RoleRepresentation realmRole = realmResource.roles().get(role).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(realmRole));

        keycloak.realm(REALM).users().get(user.getId()).update(user);
    }

    private UserRepresentation getUserRepresentation(CreateKeycloakUserDto keycloakUserDto, UserDto userDto) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDto.username());
        user.setFirstName(keycloakUserDto.firstName());
        user.setLastName(keycloakUserDto.lastName());
        user.setEmail(keycloakUserDto.email());
        user.setEnabled(true);

        if (keycloakUserDto.email() == null || keycloakUserDto.email().isEmpty()) {
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(userDto.password());
            credentialRepresentation.setTemporary(true);
            user.setCredentials(List.of(credentialRepresentation));
        }
        return user;
    }

    public List<RoleRepresentation> getRoles() {
        return keycloak.realm(REALM).roles().list();
    }
}

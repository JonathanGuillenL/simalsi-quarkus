package org.lab.simalsi.common;

import java.util.List;

public record CreateKeycloakUserDto(
    String firstName,
    String lastName,
    String email,
    String role
) {
}

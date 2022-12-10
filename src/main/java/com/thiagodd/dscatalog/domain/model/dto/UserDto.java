package com.thiagodd.dscatalog.domain.model.dto;

import com.thiagodd.dscatalog.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 482419646819388202L;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private final Set<RoleDto> roles = new HashSet<>();

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        user.getRoles().forEach(role -> this.roles.add(new RoleDto(role)));
    }

}
package com.thiagodd.dscatalog.domain.model.dto;

import com.thiagodd.dscatalog.domain.model.Role;
import com.thiagodd.dscatalog.domain.model.User;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.thiagodd.dscatalog.domain.model.Role} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6548850001229113342L;

    private Long id;
    private String authority;

//    final Set<UserDto> users = new HashSet<>();

    public RoleDto(Role role) {
        this.id = role.getId();
        this.authority = role.getAuthority();
    }
}
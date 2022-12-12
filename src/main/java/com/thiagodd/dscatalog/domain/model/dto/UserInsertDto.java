package com.thiagodd.dscatalog.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInsertDto extends UserDto {

    @Serial
    private static final long serialVersionUID = -1597520941872487031L;

    private String password;
}

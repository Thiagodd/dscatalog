package com.thiagodd.dscatalog.domain.model.dto;

import com.thiagodd.dscatalog.domain.service.validation.UserInsertValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@UserInsertValid
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInsertDto extends UserDto {

    @Serial
    private static final long serialVersionUID = -1597520941872487031L;

    private String password;
}

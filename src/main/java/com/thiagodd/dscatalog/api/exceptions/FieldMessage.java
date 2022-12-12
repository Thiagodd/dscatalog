package com.thiagodd.dscatalog.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -2289379559220095513L;

    private String fieldName;
    private String message;
}

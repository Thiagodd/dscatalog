package com.thiagodd.dscatalog.domain.service.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -5776485185338381936L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

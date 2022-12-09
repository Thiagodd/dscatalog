package com.thiagodd.dscatalog.domain.service.exceptions;

import java.io.Serial;

public class DatabaseIntegratyViolationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -5776485185338381936L;

    public DatabaseIntegratyViolationException(String message) {
        super(message);
    }
}

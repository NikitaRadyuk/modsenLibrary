package com.library.modsen.core.exceptions.exceptions;

import java.util.UUID;

public class CustomEntityNotFoundException extends RuntimeException{
    public CustomEntityNotFoundException(UUID id) {
        super("Entity not found by id " + id);
    }
    public CustomEntityNotFoundException(String isbn) {
        super("Entity not found by isbn " + isbn);
    }

}

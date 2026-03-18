package com.ncba.countryinfo.exception;

/**
 * Thrown when a requested resource (e.g. country by ID or ISO code) is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Object id) {
        super("%s not found: %s".formatted(resource, id));
    }
}

package com.ncba.countryinfo.exception;

/**
 * Thrown when the request is invalid (e.g. missing/invalid parameters).
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}

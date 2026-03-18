package com.ncba.countryinfo.exception;

import com.ncba.countryinfo.client.SoapClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SoapClientException.class)
    public ResponseEntity<Map<String, String>> handleSoapClientException(SoapClientException ex) {
        log.error("SOAP client error: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "Failed to fetch country information from external service", "detail", ex.getMessage()));
    }
}

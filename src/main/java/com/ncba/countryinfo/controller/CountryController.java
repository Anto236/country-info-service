package com.ncba.countryinfo.controller;

import com.ncba.countryinfo.dto.CountryFullInfoResponse;
import com.ncba.countryinfo.dto.CountryInfoResponse;
import com.ncba.countryinfo.dto.CountryNameRequest;
import com.ncba.countryinfo.dto.CountryNameResponse;
import com.ncba.countryinfo.dto.CountryUpdateRequest;
import com.ncba.countryinfo.exception.BadRequestException;
import com.ncba.countryinfo.exception.ResourceNotFoundException;
import com.ncba.countryinfo.model.entity.CountryInfo;
import com.ncba.countryinfo.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/countries")
@Tag(name = "Countries", description = "Country information API")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @Operation(summary = "Submit country name", description = "Receives country name, normalizes to sentence case, fetches ISO code from SOAP API")
    @PostMapping
    public ResponseEntity<CountryNameResponse> receiveCountryName(
            @Valid @RequestBody CountryNameRequest request) {
        log.info("POST /api/countries - country name: {}", request.name());
        CountryNameResponse response = countryService.processCountryName(request.name());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get full country info by ISO code", description = "Fetches full country details (name, capital, currency, etc.) from SOAP FullCountryInfo")
    @GetMapping("/full-info/{isoCode}")
    public ResponseEntity<CountryFullInfoResponse> getFullCountryInfo(
            @PathVariable String isoCode) {
        log.info("GET /api/countries/full-info/{}", isoCode);
        if (isoCode == null || isoCode.isBlank()) {
            throw new BadRequestException("ISO code is required");
        }
        CountryFullInfoResponse response = countryService.getFullCountryInfo(isoCode.trim().toUpperCase());
        if (response == null) {
            throw new ResourceNotFoundException("Country", isoCode);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List all countries", description = "Returns all persisted country records from the database")
    @GetMapping
    public ResponseEntity<List<CountryInfoResponse>> getAllCountries() {
        log.info("GET /api/countries - list all");
        List<CountryInfo> entities = countryService.findAll();
        List<CountryInfoResponse> responses = entities.stream()
                .map(CountryInfoResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get country by ID", description = "Returns a single country record by database ID")
    @GetMapping("/{id}")
    public ResponseEntity<CountryInfoResponse> getCountryById(@PathVariable Long id) {
        log.info("GET /api/countries/{}", id);
        return countryService.findById(id)
                .map(CountryInfoResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Country", id));
    }

    @Operation(summary = "Update country", description = "Updates an existing country record by ID")
    @PutMapping("/{id}")
    public ResponseEntity<CountryInfoResponse> updateCountry(
            @PathVariable Long id,
            @Valid @RequestBody CountryUpdateRequest request) {
        log.info("PUT /api/countries/{} - update", id);
        return countryService.updateById(id, request)
                .map(CountryInfoResponse::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Country", id));
    }

    @Operation(summary = "Delete country", description = "Deletes a country record by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        log.info("DELETE /api/countries/{}", id);
        if (countryService.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Country", id);
        }
        countryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

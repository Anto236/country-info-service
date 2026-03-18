package com.ncba.countryinfo.controller;

import com.ncba.countryinfo.dto.CountryNameRequest;
import com.ncba.countryinfo.dto.CountryNameResponse;
import com.ncba.countryinfo.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        CountryNameResponse response = countryService.processCountryName(request.name());
        return ResponseEntity.ok(response);
    }
}

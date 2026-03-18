package com.ncba.countryinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request body for updating country info (PUT /api/countries/{id}).
 */
@Schema(description = "Country info update request")
public record CountryUpdateRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 200, message = "Name must not exceed 200 characters")
        @Schema(description = "Country name", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
        @Schema(description = "Capital city")
        String capitalCity,
        @Schema(description = "International phone code")
        String phoneCode,
        @Schema(description = "Continent code")
        String continentCode,
        @Schema(description = "Currency ISO code")
        String currencyIsoCode,
        @Schema(description = "URL to country flag image")
        String countryFlag
) {
}

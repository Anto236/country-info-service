package com.ncba.countryinfo.dto;

import com.ncba.countryinfo.model.entity.CountryInfo;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Country info response for CRUD operations (includes database ID).
 */
@Schema(description = "Country info from database")
public record CountryInfoResponse(
        @Schema(description = "Database ID")
        Long id,
        @Schema(description = "ISO 3166-1 alpha-2 country code")
        String isoCode,
        @Schema(description = "Country name")
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
    public static CountryInfoResponse fromEntity(CountryInfo entity) {
        return new CountryInfoResponse(
                entity.getId(),
                entity.getIsoCode(),
                entity.getName(),
                entity.getCapitalCity(),
                entity.getPhoneCode(),
                entity.getContinentCode(),
                entity.getCurrencyIsoCode(),
                entity.getCountryFlag()
        );
    }
}

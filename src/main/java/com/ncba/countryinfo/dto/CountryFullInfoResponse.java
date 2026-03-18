package com.ncba.countryinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response containing full country information from the SOAP FullCountryInfo operation.
 */
@Schema(description = "Full country information from SOAP FullCountryInfo")
public record CountryFullInfoResponse(
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
    public static CountryFullInfoResponse fromEntity(com.ncba.countryinfo.model.entity.CountryInfo entity) {
        return new CountryFullInfoResponse(
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

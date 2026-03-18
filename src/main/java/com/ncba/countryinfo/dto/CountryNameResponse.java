package com.ncba.countryinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response after submitting country name - includes normalized name and ISO code")
public record CountryNameResponse(
        @Schema(description = "Country name in sentence case")
        String name,
        @Schema(description = "ISO 3166-1 alpha-2 country code from SOAP API")
        String isoCode
) {
}

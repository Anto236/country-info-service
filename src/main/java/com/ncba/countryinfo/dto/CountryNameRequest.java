package com.ncba.countryinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body containing country name")
public record CountryNameRequest(
        @NotBlank(message = "Country name is required")
        @Schema(description = "Country name (e.g. Tanzania, Kenya)", example = "Tanzania", requiredMode = Schema.RequiredMode.REQUIRED)
        String name
) {
}

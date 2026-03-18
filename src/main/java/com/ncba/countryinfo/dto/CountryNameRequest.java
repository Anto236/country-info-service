package com.ncba.countryinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body containing country name")
public record CountryNameRequest(
        @NotBlank(message = "Country name is required")
        @Size(max = 200, message = "Country name must not exceed 200 characters")
        @Schema(description = "Country name (e.g. Tanzania, Kenya)", example = "Tanzania", requiredMode = Schema.RequiredMode.REQUIRED)
        String name
) {
}

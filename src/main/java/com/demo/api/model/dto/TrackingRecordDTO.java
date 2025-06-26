package com.demo.api.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TrackingRecordDTO {

    @NotBlank(message = "created_at is required")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?(?:Z|[+-]\\d{2}:\\d{2})$",
            message = "created_at must be in RFC 3339 format (e.g., 2025-06-25T15:30:45+08:00)"
    )
    private String orderCreatedAt;

    @NotBlank(message = "origin_country_id is required")
    @Pattern(
            regexp = "^[A-Z]{2}$",
            message = "origin_country_id must be ISO 3166-1 alpha-2 format (e.g., MY)"
    )
    private String originCountryId;

    @NotBlank(message = "destination_country_id is required")
    @Pattern(
            regexp = "^[A-Z]{2}$",
            message = "destination_country_id must be ISO 3166-1 alpha-2 format (e.g., ID)"
    )
    private String destinationCountryId;

    @NotNull(message = "weight is required")
    @Digits(integer = 10, fraction = 3)
    @DecimalMin(value = "0.001", message = "weight must be greater than 0")
    private BigDecimal weight;

    @NotNull(message = "customer id is required")
    private String customerId;

    @NotNull(message = "customer name is required")
    private String customerName;

    @NotNull(message = "customer slug is required")
    private String customerSlug;
}

package com.demo.api.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TrackingRecordDTO {
    private String orderCreatedAt;
    private String originCountryId;
    private String destinationCountryId;
    private BigDecimal weight;
    private String customerId;
    private String customerName;
    private String customerSlug;
}

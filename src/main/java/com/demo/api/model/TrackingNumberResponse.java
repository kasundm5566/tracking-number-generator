package com.demo.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class TrackingNumberResponse {

    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("created_at")
    private String createdAt;
}

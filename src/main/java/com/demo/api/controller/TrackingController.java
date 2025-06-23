package com.demo.api.controller;

import com.demo.api.model.TrackingNumberResponse;
import com.demo.api.model.dto.TrackingRecordDTO;
import com.demo.api.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TrackingController {

    private final TrackingService trackingService;

    @GetMapping("/next-tracking-number")
    public TrackingNumberResponse generateNextTrackingNumber(
            @RequestParam(name = "origin_country_id") String originCountryId,
            @RequestParam(name = "destination_country_id") String destinationCountryId,
            @RequestParam double weight,
            @RequestParam(name = "created_at") String createdAt,
            @RequestParam(name = "customer_id") String customerId,
            @RequestParam(name = "customer_name") String customerName,
            @RequestParam(name = "customer_slug") String customerSlug) {

        log.info("Received request to generate tracking number with parameters: originCountryId={}, destinationCountryId={}, weight={}, createdAt={}, customerId={}, customerName={}, customerSlug={}",
                originCountryId, destinationCountryId, weight, createdAt, customerId, customerName, customerSlug);

        TrackingRecordDTO trackingRecordDTO = TrackingRecordDTO.builder()
                .orderCreatedAt(createdAt)
                .originCountryId(originCountryId)
                .destinationCountryId(destinationCountryId)
                .weight(BigDecimal.valueOf(weight))
                .customerId(customerId)
                .customerName(customerName)
                .customerSlug(customerSlug)
                .build();

        TrackingNumberResponse trackingNumberResponse = trackingService.generateNextTrackingNumber(trackingRecordDTO);

        log.info("Tracking number generating response: {}", trackingNumberResponse);

        return trackingNumberResponse;
    }
}

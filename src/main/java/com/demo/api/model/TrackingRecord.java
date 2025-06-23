package com.demo.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "tracking_record")
public class TrackingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_number", nullable = false, unique = true, length = 16)
    private String trackingNumber;

    @Column(name = "order_created_at", nullable = false)
    private OffsetDateTime orderCreatedAt;

    @Column(name = "origin_country_id", nullable = false, length = 2)
    private String originCountryId;

    @Column(name = "destination_country_id", nullable = false, length = 2)
    private String destinationCountryId;

    @Column(name = "weight", nullable = false, precision = 10, scale = 3)
    private BigDecimal weight;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_slug", nullable = false)
    private String customerSlug;

    @Column(name = "tracking_created_at", nullable = false)
    private OffsetDateTime trackingCreatedAt;
}
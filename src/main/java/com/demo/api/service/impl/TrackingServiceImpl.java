package com.demo.api.service.impl;

import com.demo.api.model.TrackingNumberResponse;
import com.demo.api.model.TrackingRecord;
import com.demo.api.model.dto.TrackingRecordDTO;
import com.demo.api.repository.TrackingRecordRepository;
import com.demo.api.service.TrackingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingServiceImpl implements TrackingService {

    private final TrackingRecordRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    @Async("taskExecutor")
    public CompletableFuture<TrackingNumberResponse> generateNextTrackingNumber(TrackingRecordDTO trackingRecordDTO) {
        String trackingNumber = generateRandomTrackingNumber(trackingRecordDTO.getOriginCountryId(), trackingRecordDTO.getDestinationCountryId());
        String databaseUpdatedTime = updateDatabaseWithTrackingNumber(trackingNumber, trackingRecordDTO);
        TrackingNumberResponse trackingNumberResponse = TrackingNumberResponse.builder()
                .trackingNumber(trackingNumber)
                .createdAt(databaseUpdatedTime)
                .build();
        return CompletableFuture.completedFuture(trackingNumberResponse);
    }

    private String generateRandomTrackingNumber(String origin, String destination) {
        log.info("Generating next tracking number.");
        long sequence = getNextSequenceValue();
        String base36 = Long.toString(sequence, 36).toUpperCase();
        String prefix = (origin + destination).toUpperCase();
        String randomPart = generateRandomAlphaNumeric(6);

        String rawTrackingNumber = prefix + base36 + randomPart;

        String trackingNumber = rawTrackingNumber.length() > 16
                ? rawTrackingNumber.substring(0, 16)
                : String.format("%-16s", rawTrackingNumber).replace(' ', '0');

        log.info("Generated tracking number: {}", trackingNumber);
        return trackingNumber;
    }

    private long getNextSequenceValue() {
        return ((Number) entityManager.createNativeQuery("SELECT nextval('tracking_number_seq')").getSingleResult()).longValue();
    }

    private String generateRandomAlphaNumeric(int length) {
        StringBuilder result = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            result.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return result.toString();
    }

    private String updateDatabaseWithTrackingNumber(String trackingNumber, TrackingRecordDTO trackingRecordDTO) {
        log.info("Updating database with tracking number: {}", trackingNumber);

        OffsetDateTime generatedAt = OffsetDateTime.parse(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")));

        TrackingRecord trackingRecord = new TrackingRecord();
        trackingRecord.setTrackingNumber(trackingNumber);
        trackingRecord.setOrderCreatedAt(OffsetDateTime.parse(trackingRecordDTO.getOrderCreatedAt()));
        trackingRecord.setOriginCountryId(trackingRecordDTO.getOriginCountryId());
        trackingRecord.setDestinationCountryId(trackingRecordDTO.getDestinationCountryId());
        trackingRecord.setWeight(trackingRecordDTO.getWeight());
        trackingRecord.setCustomerId(UUID.fromString(trackingRecordDTO.getCustomerId()));
        trackingRecord.setCustomerName(trackingRecordDTO.getCustomerName());
        trackingRecord.setCustomerSlug(trackingRecordDTO.getCustomerSlug());
        trackingRecord.setTrackingCreatedAt(generatedAt);

        repository.save(trackingRecord);

        log.info("Updated the database");

        return generatedAt.toString();
    }
}

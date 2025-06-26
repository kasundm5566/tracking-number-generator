package com.demo.api.service.impl;

import com.demo.api.exception.ValidationException;
import com.demo.api.model.TrackingNumberResponse;
import com.demo.api.model.dto.TrackingRecordDTO;
import com.demo.api.repository.TrackingRecordRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackingServiceImplTest {

    @Mock
    private TrackingRecordRepository repository;

    @Mock
    private Validator validator;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TrackingServiceImpl trackingService;

    private TrackingRecordDTO validTrackingRecordDTO;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        java.lang.reflect.Field entityManagerField = TrackingServiceImpl.class.getDeclaredField("entityManager");
        entityManagerField.setAccessible(true);
        entityManagerField.set(trackingService, entityManager);

        validTrackingRecordDTO = TrackingRecordDTO.builder()
                .orderCreatedAt("2025-06-26T21:04:44+00:00")
                .originCountryId("MY")
                .destinationCountryId("ID")
                .weight(BigDecimal.valueOf(5.0))
                .customerId("123e4567-e89b-12d3-a456-426614174000")
                .customerName("Customer Name")
                .customerSlug("Customer-Slug")
                .build();
    }

    @Test
    void testGenerateNextTrackingNumber_Success() throws Exception {
        when(validator.validate(validTrackingRecordDTO)).thenReturn(Collections.emptySet());

        jakarta.persistence.Query mockQuery = mock(jakarta.persistence.Query.class);
        when(entityManager.createNativeQuery("SELECT nextval('tracking_number_seq')")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(12345L);

        when(repository.save(any())).thenReturn(null); // Fix applied here

        CompletableFuture<TrackingNumberResponse> responseFuture = trackingService.generateNextTrackingNumber(validTrackingRecordDTO);

        assertNotNull(responseFuture);

        TrackingNumberResponse response = responseFuture.get();
        assertNotNull(response.getTrackingNumber());
        assertNotNull(response.getCreatedAt());

        verify(validator).validate(validTrackingRecordDTO);
        verify(entityManager).createNativeQuery("SELECT nextval('tracking_number_seq')");
        verify(mockQuery).getSingleResult();
        verify(repository).save(any());
    }

    @Test
    void testGenerateNextTrackingNumber_WeightValidationFailure() {
        ConstraintViolation<TrackingRecordDTO> violation = mock(ConstraintViolation.class);
        Path mockPath = mock(Path.class);

        when(mockPath.toString()).thenReturn("weight");
        when(violation.getPropertyPath()).thenReturn(mockPath);
        when(violation.getMessage()).thenReturn("must be greater than 0");

        Set<ConstraintViolation<TrackingRecordDTO>> violations = Collections.singleton(violation);

        when(validator.validate(validTrackingRecordDTO)).thenReturn(violations);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            trackingService.generateNextTrackingNumber(validTrackingRecordDTO);
        });

        assertTrue(exception.getMessage().contains("weight: must be greater than 0"));

        verify(validator).validate(validTrackingRecordDTO);
        verifyNoInteractions(repository);
    }

    @Test
    void testGenerateNextTrackingNumber_CustomerNameValidationFailure() {
        ConstraintViolation<TrackingRecordDTO> violation = mock(ConstraintViolation.class);
        Path mockPath = mock(Path.class);

        when(mockPath.toString()).thenReturn("customer_name");
        when(violation.getPropertyPath()).thenReturn(mockPath);
        when(violation.getMessage()).thenReturn("customer name is required");

        Set<ConstraintViolation<TrackingRecordDTO>> violations = Collections.singleton(violation);

        when(validator.validate(validTrackingRecordDTO)).thenReturn(violations);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            trackingService.generateNextTrackingNumber(validTrackingRecordDTO);
        });

        assertTrue(exception.getMessage().contains("customer_name: customer name is required"));

        verify(validator).validate(validTrackingRecordDTO);
        verifyNoInteractions(repository);
    }
}
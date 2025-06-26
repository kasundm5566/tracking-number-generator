package com.demo.api.service;

import com.demo.api.model.TrackingNumberResponse;
import com.demo.api.model.dto.TrackingRecordDTO;

import java.util.concurrent.CompletableFuture;

public interface TrackingService {

    CompletableFuture<TrackingNumberResponse> generateNextTrackingNumber(TrackingRecordDTO trackingRecordDTO);
}

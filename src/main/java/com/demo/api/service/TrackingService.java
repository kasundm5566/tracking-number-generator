package com.demo.api.service;

import com.demo.api.model.TrackingNumberResponse;
import com.demo.api.model.dto.TrackingRecordDTO;

public interface TrackingService {

    TrackingNumberResponse generateNextTrackingNumber(TrackingRecordDTO trackingRecordDTO);
}

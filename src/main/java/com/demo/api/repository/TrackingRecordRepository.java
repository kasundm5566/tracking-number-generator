package com.demo.api.repository;

import com.demo.api.model.TrackingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingRecordRepository extends JpaRepository<TrackingRecord, Long> {
}
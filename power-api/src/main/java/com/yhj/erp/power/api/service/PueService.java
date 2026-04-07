package com.yhj.erp.power.api.service;

import com.yhj.erp.power.api.dto.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * PUE service interface.
 */
public interface PueService {

    /**
     * Record PUE data for a data center.
     */
    PueDataDto recordPue(PueRecordRequest request);

    /**
     * Get the latest PUE for a data center.
     */
    PueDataDto getLatestPue(String datacenterId);

    /**
     * Get PUE history for a data center within a time range.
     */
    List<PueDataDto> getPueHistory(String datacenterId, LocalDateTime start, LocalDateTime end);

    /**
     * Calculate average PUE for a data center within a time range.
     */
    Double calculateAveragePue(String datacenterId, LocalDateTime start, LocalDateTime end);
}
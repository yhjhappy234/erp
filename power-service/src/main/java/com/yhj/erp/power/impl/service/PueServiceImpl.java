package com.yhj.erp.power.impl.service;

import com.yhj.erp.common.constant.ErrorCode;
import com.yhj.erp.common.exception.ResourceNotFoundException;
import com.yhj.erp.power.api.dto.*;
import com.yhj.erp.power.api.service.PueService;
import com.yhj.erp.power.impl.entity.PueDataEntity;
import com.yhj.erp.power.impl.mapper.PueDataMapper;
import com.yhj.erp.power.impl.repository.PueDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * PUE service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PueServiceImpl implements PueService {

    private final PueDataRepository pueDataRepository;
    private final PueDataMapper pueDataMapper;

    @Override
    @Transactional
    public PueDataDto recordPue(PueRecordRequest request) {
        log.info("Recording PUE for datacenter: {}", request.getDatacenterId());

        LocalDateTime recordedAt = request.getRecordedAt() != null ? request.getRecordedAt() : LocalDateTime.now();

        PueDataEntity entity = PueDataEntity.builder()
                .datacenterId(request.getDatacenterId())
                .pueValue(request.getPueValue())
                .itLoad(request.getItLoad())
                .totalLoad(request.getTotalLoad())
                .recordedAt(recordedAt)
                .build();

        PueDataEntity saved = pueDataRepository.save(entity);
        log.info("PUE recorded: id={}, pueValue={}", saved.getId(), saved.getPueValue());

        return pueDataMapper.toDto(saved);
    }

    @Override
    public PueDataDto getLatestPue(String datacenterId) {
        log.info("Getting latest PUE for datacenter: {}", datacenterId);
        return pueDataRepository.findFirstByDatacenterIdAndDeletedFalseOrderByRecordedAtDesc(datacenterId)
                .map(pueDataMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.NOT_FOUND,
                        "No PUE data found for datacenter: " + datacenterId));
    }

    @Override
    public List<PueDataDto> getPueHistory(String datacenterId, LocalDateTime start, LocalDateTime end) {
        log.info("Getting PUE history for datacenter: {}, from {} to {}", datacenterId, start, end);
        List<PueDataEntity> entities = pueDataRepository
                .findByDatacenterIdAndDeletedFalseAndRecordedAtBetweenOrderByRecordedAtDesc(datacenterId, start, end);
        return pueDataMapper.toDtoList(entities);
    }

    @Override
    public Double calculateAveragePue(String datacenterId, LocalDateTime start, LocalDateTime end) {
        log.info("Calculating average PUE for datacenter: {}, from {} to {}", datacenterId, start, end);
        List<PueDataEntity> entities = pueDataRepository
                .findByDatacenterIdAndDeletedFalseAndRecordedAtBetweenOrderByRecordedAtDesc(datacenterId, start, end);

        if (entities.isEmpty()) {
            return null;
        }

        double avg = entities.stream()
                .filter(e -> e.getPueValue() != null)
                .mapToDouble(e -> e.getPueValue().doubleValue())
                .average()
                .orElse(0.0);

        log.info("Average PUE calculated: {}", avg);
        return avg;
    }
}
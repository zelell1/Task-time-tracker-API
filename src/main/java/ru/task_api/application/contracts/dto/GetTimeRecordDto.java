package ru.task_api.application.contracts.dto;

import java.time.Instant;
import java.util.UUID;

public record GetTimeRecordDto(
    UUID id,
    UUID userId,
    UUID taskId,
    Instant startTime,
    Instant endTime,
    String description
) {}

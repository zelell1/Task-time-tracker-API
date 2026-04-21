package ru.task_api.domain.entities;

import java.time.Instant;
import java.util.UUID;

public record TimeRecord(
    UUID id,
    UUID userId,
    UUID taskId,
    Instant startTime,
    Instant endTime,
    String description
) {}

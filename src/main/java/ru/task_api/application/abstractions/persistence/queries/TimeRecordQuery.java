package ru.task_api.application.abstractions.persistence.queries;

import java.time.Instant;
import java.util.UUID;

public record TimeRecordQuery(
    UUID userId,
    Instant startTime,
    Instant endTime
) {}

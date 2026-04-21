package ru.task_api.application.contracts.request;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;

public record GetTimeRecordsRequest(
    @NotNull Instant startTime,
    @NotNull Instant endTime
) {}

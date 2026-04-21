package ru.task_api.application.contracts.request;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTimeRecordRequest(
    @NotNull UUID taskId,
    @NotNull Instant startTime,
    @NotNull Instant endTime,
    @NotBlank String description
) {}

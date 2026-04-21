package ru.task_api.application.contracts.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import ru.task_api.domain.enums.TaskStatus;

public record UpdateTaskStatusRequest(
    @NotNull UUID taskId,
    @NotNull TaskStatus status
) {}

package ru.task_api.application.contracts.request;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(
    @NotBlank String name,
    String description
) {}

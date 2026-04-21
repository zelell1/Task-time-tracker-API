package ru.task_api.application.contracts.dto;

import java.util.UUID;

import ru.task_api.domain.enums.TaskStatus;

public record GetTaskDto(
    UUID id,
    String name,
    String description,
    TaskStatus status
) {}

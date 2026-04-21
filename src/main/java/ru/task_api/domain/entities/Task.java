package ru.task_api.domain.entities;

import java.util.UUID;
import ru.task_api.domain.enums.TaskStatus;

public record Task(
    UUID id,
    String name,
    String description,
    TaskStatus status
) {}

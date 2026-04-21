package ru.task_api.domain.entities;

import java.util.UUID;

public record User(
    UUID id,
    String username,
    String password
) {}

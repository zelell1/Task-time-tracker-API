package ru.task_api.application.abstractions.persistence.repositories;

import java.util.UUID;
import ru.task_api.domain.entities.Task;

public interface TaskRepository {
    void addTask(Task task);

    void deleteTaskById(UUID id);

    Task findById(UUID id);

    void updateTask(Task task);
}

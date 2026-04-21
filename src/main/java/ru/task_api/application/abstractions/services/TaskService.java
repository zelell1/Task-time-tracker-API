package ru.task_api.application.abstractions.services;

import java.util.UUID;

import ru.task_api.application.contracts.dto.CreateTaskDto;
import ru.task_api.application.contracts.dto.GetTaskDto;
import ru.task_api.application.contracts.request.CreateTaskRequest;
import ru.task_api.application.contracts.request.UpdateTaskStatusRequest;

public interface TaskService {
    CreateTaskDto addTask(CreateTaskRequest request);

    GetTaskDto getTaskById(UUID id);

    void updateTaskStatus(UpdateTaskStatusRequest request);
}

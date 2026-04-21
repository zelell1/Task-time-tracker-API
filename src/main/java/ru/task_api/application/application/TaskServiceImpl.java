package ru.task_api.application.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ru.task_api.application.abstractions.persistence.repositories.TaskRepository;
import ru.task_api.application.abstractions.services.TaskService;
import ru.task_api.application.contracts.dto.CreateTaskDto;
import ru.task_api.application.contracts.dto.GetTaskDto;
import ru.task_api.application.contracts.mapper.TaskDtoMapper;
import ru.task_api.application.contracts.request.CreateTaskRequest;
import ru.task_api.application.contracts.request.UpdateTaskStatusRequest;
import ru.task_api.application.exceptions.NotFoundException;
import ru.task_api.domain.entities.Task;
import ru.task_api.domain.enums.TaskStatus;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskDtoMapper mapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskDtoMapper mapper) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
    }

    @Override
    public CreateTaskDto addTask(CreateTaskRequest request) {
        Task task = new Task(UUID.randomUUID(), request.name(), request.description(), TaskStatus.NEW);
        taskRepository.addTask(task);
        return mapper.toCreateDto(task);
    }

    @Override
    public GetTaskDto getTaskById(UUID id) {
        Task task = taskRepository.findById(id);
        if (task == null) throw new NotFoundException("Task not found: " + id);
        return mapper.toDto(task);
    }

    @Override
    public void updateTaskStatus(UpdateTaskStatusRequest request) {
        Task existing = taskRepository.findById(request.taskId());
        if (existing == null) throw new NotFoundException("Task not found: " + request.taskId());
        Task updated = new Task(existing.id(), existing.name(), existing.description(), request.status());
        taskRepository.updateTask(updated);
    }
}

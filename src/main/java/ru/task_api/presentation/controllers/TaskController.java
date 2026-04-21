package ru.task_api.presentation.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.task_api.application.abstractions.services.TaskService;
import ru.task_api.application.contracts.dto.CreateTaskDto;
import ru.task_api.application.contracts.dto.GetTaskDto;
import ru.task_api.application.contracts.request.CreateTaskRequest;
import ru.task_api.application.contracts.request.UpdateTaskStatusRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTaskDto createTask(@Valid @RequestBody CreateTaskRequest request) {
        return service.addTask(request);
    }

    @GetMapping("/{id}")
    public GetTaskDto getTaskById(@PathVariable UUID id) {
        return service.getTaskById(id);
    }

    @PatchMapping("/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTaskStatus(@Valid @RequestBody UpdateTaskStatusRequest request) {
        service.updateTaskStatus(request);
    }
}

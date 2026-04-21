package ru.task_api.application.contracts.mapper;

import org.springframework.stereotype.Component;
import ru.task_api.application.contracts.dto.CreateTaskDto;
import ru.task_api.application.contracts.dto.GetTaskDto;
import ru.task_api.domain.entities.Task;

@Component
public class TaskDtoMapper {

    public GetTaskDto toDto(Task task) {
        return new GetTaskDto(task.id(), task.name(), task.description(), task.status());
    }

    public CreateTaskDto toCreateDto(Task task) {
        return new CreateTaskDto(task.id());
    }
}

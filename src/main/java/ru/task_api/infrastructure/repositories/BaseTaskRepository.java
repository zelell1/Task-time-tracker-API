package ru.task_api.infrastructure.repositories;

import java.util.UUID;

import ru.task_api.application.abstractions.persistence.repositories.TaskRepository;
import ru.task_api.domain.entities.Task;
import org.springframework.stereotype.Repository;
import ru.task_api.infrastructure.mappers.TaskMapper;

@Repository
public class BaseTaskRepository implements TaskRepository {

    private final TaskMapper mapper;

    public BaseTaskRepository(TaskMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void addTask(Task task) {
        mapper.addTask(task);
    }

    @Override
    public void deleteTaskById(UUID id) {
        mapper.deleteTaskById(id);
    }

    @Override
    public Task findById(UUID id) {
        return mapper.findById(id);
    }

    @Override
    public void updateTask(Task task) {
        mapper.updateTask(task);
    }
}

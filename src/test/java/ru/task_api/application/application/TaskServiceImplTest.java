package ru.task_api.application.application;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.task_api.application.abstractions.persistence.repositories.TaskRepository;
import ru.task_api.application.contracts.dto.CreateTaskDto;
import ru.task_api.application.contracts.dto.GetTaskDto;
import ru.task_api.application.contracts.mapper.TaskDtoMapper;
import ru.task_api.application.contracts.request.CreateTaskRequest;
import ru.task_api.application.contracts.request.UpdateTaskStatusRequest;
import ru.task_api.domain.entities.Task;
import ru.task_api.domain.enums.TaskStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskDtoMapper mapper;

    @InjectMocks
    private TaskServiceImpl service;

    @Test
    void addTask_persistsTaskWithNewStatusAndReturnsCreateDto() {
        CreateTaskRequest request = new CreateTaskRequest("Fix bug", "critical");
        CreateTaskDto expected = new CreateTaskDto(UUID.randomUUID());
        when(mapper.toCreateDto(any(Task.class))).thenReturn(expected);

        CreateTaskDto result = service.addTask(request);

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).addTask(captor.capture());
        Task saved = captor.getValue();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.name()).isEqualTo("Fix bug");
        assertThat(saved.description()).isEqualTo("critical");
        assertThat(saved.status()).isEqualTo(TaskStatus.NEW);

        verify(mapper).toCreateDto(saved);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getTaskById_returnsMappedDtoFromRepository() {
        UUID id = UUID.randomUUID();
        Task task = new Task(id, "Review PR", "urgent", TaskStatus.IN_PROGRESS);
        GetTaskDto expected = new GetTaskDto(id, "Review PR", "urgent", TaskStatus.IN_PROGRESS);
        when(taskRepository.findById(id)).thenReturn(task);
        when(mapper.toDto(task)).thenReturn(expected);

        GetTaskDto result = service.getTaskById(id);

        assertThat(result).isEqualTo(expected);
        verify(taskRepository).findById(id);
        verify(mapper).toDto(task);
    }

    @Test
    void updateTaskStatus_updatesStatusKeepingOtherFields() {
        UUID id = UUID.randomUUID();
        Task existing = new Task(id, "Deploy", "prod", TaskStatus.NEW);
        when(taskRepository.findById(id)).thenReturn(existing);

        service.updateTaskStatus(new UpdateTaskStatusRequest(id, TaskStatus.DONE));

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).updateTask(captor.capture());
        Task updated = captor.getValue();
        assertThat(updated.id()).isEqualTo(id);
        assertThat(updated.name()).isEqualTo("Deploy");
        assertThat(updated.description()).isEqualTo("prod");
        assertThat(updated.status()).isEqualTo(TaskStatus.DONE);

        verifyNoMoreInteractions(mapper);
    }
}

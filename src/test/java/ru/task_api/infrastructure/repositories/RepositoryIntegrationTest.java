package ru.task_api.infrastructure.repositories;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.task_api.application.abstractions.persistence.queries.TimeRecordQuery;
import ru.task_api.application.abstractions.persistence.repositories.TaskRepository;
import ru.task_api.application.abstractions.persistence.repositories.TimeRecordRepository;
import ru.task_api.application.abstractions.persistence.repositories.UsersRepository;
import ru.task_api.domain.entities.Task;
import ru.task_api.domain.entities.TimeRecord;
import ru.task_api.domain.entities.User;
import ru.task_api.domain.enums.TaskStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Transactional
class RepositoryIntegrationTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void datasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TimeRecordRepository timeRecordRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void addAndFindTask_roundtrip() {
        Task task = new Task(UUID.randomUUID(), "Review PR", "critical", TaskStatus.NEW);
        taskRepository.addTask(task);

        Task found = taskRepository.findById(task.id());

        assertThat(found).isNotNull();
        assertThat(found.id()).isEqualTo(task.id());
        assertThat(found.name()).isEqualTo("Review PR");
        assertThat(found.status()).isEqualTo(TaskStatus.NEW);
    }

    @Test
    void updateTask_changesStatusAndName() {
        Task task = new Task(UUID.randomUUID(), "Old name", null, TaskStatus.NEW);
        taskRepository.addTask(task);

        Task updated = new Task(task.id(), "New name", null, TaskStatus.DONE);
        taskRepository.updateTask(updated);

        Task found = taskRepository.findById(task.id());
        assertThat(found.name()).isEqualTo("New name");
        assertThat(found.status()).isEqualTo(TaskStatus.DONE);
    }

    @Test
    void deleteTask_removesFromDatabase() {
        Task task = new Task(UUID.randomUUID(), "To delete", null, TaskStatus.NEW);
        taskRepository.addTask(task);

        taskRepository.deleteTaskById(task.id());

        assertThat(taskRepository.findById(task.id())).isNull();
    }

    @Test
    void addAndFindTimeRecord_roundtrip() {
        User user = new User(UUID.randomUUID(), "testuser_" + UUID.randomUUID(), "hash");
        usersRepository.addUser(user);

        Task task = new Task(UUID.randomUUID(), "Some task", null, TaskStatus.NEW);
        taskRepository.addTask(task);

        Instant start = Instant.parse("2026-04-21T09:00:00Z");
        Instant end = Instant.parse("2026-04-21T11:00:00Z");
        TimeRecord record = new TimeRecord(UUID.randomUUID(), user.id(), task.id(), start, end, "deep work");
        timeRecordRepository.addTimeRecord(record);

        TimeRecord found = timeRecordRepository.findById(record.id());
        assertThat(found).isNotNull();
        assertThat(found.userId()).isEqualTo(user.id());
        assertThat(found.taskId()).isEqualTo(task.id());
        assertThat(found.description()).isEqualTo("deep work");
    }

    @Test
    void query_returnsOnlyRecordsMatchingUserAndPeriod() {
        User user1 = new User(UUID.randomUUID(), "worker_" + UUID.randomUUID(), "hash");
        User user2 = new User(UUID.randomUUID(), "other_" + UUID.randomUUID(), "hash");
        usersRepository.addUser(user1);
        usersRepository.addUser(user2);

        Task task = new Task(UUID.randomUUID(), "Task A", null, TaskStatus.IN_PROGRESS);
        taskRepository.addTask(task);

        Instant start = Instant.parse("2026-04-01T00:00:00Z");
        Instant mid = Instant.parse("2026-04-15T00:00:00Z");
        Instant end = Instant.parse("2026-04-30T23:59:59Z");
        Instant outside = Instant.parse("2026-05-01T00:00:00Z");
        Instant outsideEnd = Instant.parse("2026-05-02T00:00:00Z");

        TimeRecord r1 = new TimeRecord(UUID.randomUUID(), user1.id(), task.id(), start, mid, "in period");
        TimeRecord r2 = new TimeRecord(UUID.randomUUID(), user1.id(), task.id(), outside, outsideEnd, "out of period");
        TimeRecord r3 = new TimeRecord(UUID.randomUUID(), user2.id(), task.id(), start, mid, "wrong user");

        timeRecordRepository.addTimeRecord(r1);
        timeRecordRepository.addTimeRecord(r2);
        timeRecordRepository.addTimeRecord(r3);

        List<TimeRecord> result = timeRecordRepository.query(new TimeRecordQuery(user1.id(), start, end));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(r1.id());
    }

    @Test
    void findByUsername_returnsCorrectUser() {
        String name = "unique_user_" + UUID.randomUUID();
        User user = new User(UUID.randomUUID(), name, "bcrypt_hash");
        usersRepository.addUser(user);

        User found = usersRepository.findByUsername(name);

        assertThat(found).isNotNull();
        assertThat(found.id()).isEqualTo(user.id());
        assertThat(found.username()).isEqualTo(name);
    }
}

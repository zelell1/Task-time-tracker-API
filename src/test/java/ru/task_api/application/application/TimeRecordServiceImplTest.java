package ru.task_api.application.application;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import ru.task_api.application.abstractions.persistence.queries.TimeRecordQuery;
import ru.task_api.application.abstractions.persistence.repositories.TimeRecordRepository;
import ru.task_api.application.abstractions.persistence.repositories.UsersRepository;
import ru.task_api.application.contracts.dto.CreateTimeRecordDto;
import ru.task_api.application.contracts.dto.GetTimeRecordDto;
import ru.task_api.application.contracts.mapper.TimeRecordDtoMapper;
import ru.task_api.application.contracts.request.CreateTimeRecordRequest;
import ru.task_api.application.contracts.request.GetTimeRecordsRequest;
import ru.task_api.domain.entities.TimeRecord;
import ru.task_api.domain.entities.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeRecordServiceImplTest {

    private static final String USERNAME = "alice";

    @Mock
    private TimeRecordRepository timeRecordRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private TimeRecordDtoMapper mapper;

    @InjectMocks
    private TimeRecordServiceImpl service;

    @BeforeEach
    void setAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(USERNAME, "n/a", List.of()));
        SecurityContextHolder.setContext(context);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createTimeRecord_persistsRecordWithAuthenticatedUserId() {
        UUID userId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        Instant start = Instant.parse("2026-04-21T09:00:00Z");
        Instant end = Instant.parse("2026-04-21T10:30:00Z");
        CreateTimeRecordRequest request = new CreateTimeRecordRequest(taskId, start, end, "coding");

        when(usersRepository.findByUsername(USERNAME))
            .thenReturn(new User(userId, USERNAME, "hashed"));
        CreateTimeRecordDto expected = new CreateTimeRecordDto(UUID.randomUUID());
        when(mapper.toCreateDto(any(TimeRecord.class))).thenReturn(expected);

        CreateTimeRecordDto result = service.createTimeRecord(request);

        ArgumentCaptor<TimeRecord> captor = ArgumentCaptor.forClass(TimeRecord.class);
        verify(timeRecordRepository).addTimeRecord(captor.capture());
        TimeRecord saved = captor.getValue();
        assertThat(saved.id()).isNotNull();
        assertThat(saved.userId()).isEqualTo(userId);
        assertThat(saved.taskId()).isEqualTo(taskId);
        assertThat(saved.startTime()).isEqualTo(start);
        assertThat(saved.endTime()).isEqualTo(end);
        assertThat(saved.description()).isEqualTo("coding");

        verify(mapper).toCreateDto(saved);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void getTimeRecord_queriesRepositoryByAuthenticatedUserAndMapsEach() {
        UUID userId = UUID.randomUUID();
        Instant start = Instant.parse("2026-04-01T00:00:00Z");
        Instant end = Instant.parse("2026-04-30T23:59:59Z");
        GetTimeRecordsRequest request = new GetTimeRecordsRequest(start, end);

        when(usersRepository.findByUsername(USERNAME))
            .thenReturn(new User(userId, USERNAME, "hashed"));

        TimeRecord r1 = new TimeRecord(UUID.randomUUID(), userId, UUID.randomUUID(), start, end, "a");
        TimeRecord r2 = new TimeRecord(UUID.randomUUID(), userId, UUID.randomUUID(), start, end, "b");
        GetTimeRecordDto d1 = new GetTimeRecordDto(r1.id(), userId, r1.taskId(), start, end, "a");
        GetTimeRecordDto d2 = new GetTimeRecordDto(r2.id(), userId, r2.taskId(), start, end, "b");

        ArgumentCaptor<TimeRecordQuery> queryCaptor = ArgumentCaptor.forClass(TimeRecordQuery.class);
        when(timeRecordRepository.query(queryCaptor.capture())).thenReturn(List.of(r1, r2));
        when(mapper.toDto(r1)).thenReturn(d1);
        when(mapper.toDto(r2)).thenReturn(d2);

        List<GetTimeRecordDto> result = service.getTimeRecord(request);

        assertThat(result).containsExactly(d1, d2);
        TimeRecordQuery query = queryCaptor.getValue();
        assertThat(query.userId()).isEqualTo(userId);
        assertThat(query.startTime()).isEqualTo(start);
        assertThat(query.endTime()).isEqualTo(end);
    }
}

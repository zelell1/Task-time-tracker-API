package ru.task_api.application.application;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.task_api.application.abstractions.persistence.repositories.TimeRecordRepository;
import ru.task_api.application.abstractions.persistence.repositories.UsersRepository;
import ru.task_api.application.abstractions.services.TimeRecordService;
import ru.task_api.application.contracts.dto.CreateTimeRecordDto;
import ru.task_api.application.contracts.dto.GetTimeRecordDto;
import ru.task_api.application.contracts.mapper.TimeRecordDtoMapper;
import ru.task_api.application.contracts.request.CreateTimeRecordRequest;
import ru.task_api.application.contracts.request.GetTimeRecordsRequest;
import ru.task_api.application.abstractions.persistence.queries.TimeRecordQuery;
import ru.task_api.domain.entities.TimeRecord;
import ru.task_api.domain.entities.User;

@Service
public class TimeRecordServiceImpl implements TimeRecordService {

    private final TimeRecordRepository timeRecordRepository;

    private final UsersRepository usersRepository;
    
    private final TimeRecordDtoMapper mapper;

    public TimeRecordServiceImpl(
        TimeRecordRepository timeRecordRepository, 
        UsersRepository usersRepository,
        TimeRecordDtoMapper mapper) {
        this.timeRecordRepository = timeRecordRepository;
        this.usersRepository = usersRepository;
        this.mapper = mapper;
    }

    @Override
    public CreateTimeRecordDto createTimeRecord(CreateTimeRecordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = usersRepository.findByUsername(authentication.getName());
        TimeRecord record = new TimeRecord(
            UUID.randomUUID(),
            user.id(),
            request.taskId(),
            request.startTime(),
            request.endTime(),
            request.description()
        );
        timeRecordRepository.addTimeRecord(record);
        return mapper.toCreateDto(record);
    }

    @Override
    public List<GetTimeRecordDto> getTimeRecord(GetTimeRecordsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = usersRepository.findByUsername(authentication.getName());
        TimeRecordQuery query = new TimeRecordQuery(user.id(), request.startTime(), request.endTime());
        return timeRecordRepository.query(query).stream()
            .map(mapper::toDto)
            .toList();
    }
}

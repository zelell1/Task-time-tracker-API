package ru.task_api.infrastructure.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import ru.task_api.application.abstractions.persistence.queries.TimeRecordQuery;
import ru.task_api.application.abstractions.persistence.repositories.TimeRecordRepository;
import ru.task_api.domain.entities.TimeRecord;
import ru.task_api.infrastructure.mappers.TimeRecordMapper;

@Repository
public class BaseTimeRecordRepository implements TimeRecordRepository {

    private final TimeRecordMapper mapper;

    public BaseTimeRecordRepository(TimeRecordMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void addTimeRecord(TimeRecord timeRecord) {
        mapper.addTimeRecord(timeRecord);
    }

    @Override
    public void deleteRecordById(UUID id) {
        mapper.deleteRecordById(id);
    }

    @Override
    public TimeRecord findById(UUID id) {
        return mapper.findById(id);
    }

    @Override
    public List<TimeRecord> query(TimeRecordQuery query) {
        return mapper.query(query);
    }
}

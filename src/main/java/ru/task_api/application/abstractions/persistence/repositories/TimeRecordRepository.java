package ru.task_api.application.abstractions.persistence.repositories;

import java.util.UUID;
import java.util.List;

import ru.task_api.application.abstractions.persistence.queries.TimeRecordQuery;
import ru.task_api.domain.entities.TimeRecord;

public interface TimeRecordRepository {
    void addTimeRecord(TimeRecord timeRecord);

    void deleteRecordById(UUID id);

    TimeRecord findById(UUID id);

    List<TimeRecord> query(TimeRecordQuery query);
}

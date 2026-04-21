package ru.task_api.application.contracts.mapper;

import org.springframework.stereotype.Component;
import ru.task_api.application.contracts.dto.CreateTimeRecordDto;
import ru.task_api.application.contracts.dto.GetTimeRecordDto;
import ru.task_api.domain.entities.TimeRecord;

@Component
public class TimeRecordDtoMapper {

    public GetTimeRecordDto toDto(TimeRecord record) {
        return new GetTimeRecordDto(
            record.id(),
            record.userId(),
            record.taskId(),
            record.startTime(),
            record.endTime(),
            record.description()
        );
    }

    public CreateTimeRecordDto toCreateDto(TimeRecord record) {
        return new CreateTimeRecordDto(record.id());
    }
}

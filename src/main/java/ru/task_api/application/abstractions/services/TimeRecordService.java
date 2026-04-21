package ru.task_api.application.abstractions.services;

import java.util.List;

import ru.task_api.application.contracts.dto.CreateTimeRecordDto;
import ru.task_api.application.contracts.dto.GetTimeRecordDto;
import ru.task_api.application.contracts.request.CreateTimeRecordRequest;
import ru.task_api.application.contracts.request.GetTimeRecordsRequest;

public interface TimeRecordService {
    CreateTimeRecordDto createTimeRecord(CreateTimeRecordRequest request);

    List<GetTimeRecordDto> getTimeRecord(GetTimeRecordsRequest request);
}

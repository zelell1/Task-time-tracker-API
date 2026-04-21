package ru.task_api.presentation.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.task_api.application.abstractions.services.TimeRecordService;
import ru.task_api.application.contracts.dto.CreateTimeRecordDto;
import ru.task_api.application.contracts.dto.GetTimeRecordDto;
import ru.task_api.application.contracts.request.CreateTimeRecordRequest;
import ru.task_api.application.contracts.request.GetTimeRecordsRequest;

@RestController
@RequestMapping("/time-records")
public class TimeRecordController {

    private final TimeRecordService service;

    public TimeRecordController(TimeRecordService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTimeRecordDto createTimeRecord(@Valid @RequestBody CreateTimeRecordRequest request) {
        return service.createTimeRecord(request);
    }

    @PostMapping("/by-period")
    public List<GetTimeRecordDto> getByPeriod(@Valid @RequestBody GetTimeRecordsRequest request) {
        return service.getTimeRecord(request);
    }
}

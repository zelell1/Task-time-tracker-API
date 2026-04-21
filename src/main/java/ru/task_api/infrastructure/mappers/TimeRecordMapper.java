package ru.task_api.infrastructure.mappers;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.*;

import ru.task_api.application.abstractions.persistence.queries.TimeRecordQuery;
import ru.task_api.domain.entities.TimeRecord;

@Mapper
public interface TimeRecordMapper {
    @Insert("INSERT INTO time_records (id, user_id, task_id, start_time, end_time, description) " +
            "VALUES (#{id}, #{userId}, #{taskId}, #{startTime}, #{endTime}, #{description})")
    void addTimeRecord(TimeRecord timeRecord);

    @Delete("DELETE FROM time_records WHERE id = #{id}")
    void deleteRecordById(UUID id);

    @Select("SELECT * FROM time_records WHERE id = #{id}")
    TimeRecord findById(UUID id);

    @Select("SELECT * FROM time_records WHERE user_id = #{userId} AND start_time >= #{startTime} AND end_time <= #{endTime}")
    List<TimeRecord> query(TimeRecordQuery query);
}

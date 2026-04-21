package ru.task_api.infrastructure.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.UUID;
import ru.task_api.domain.entities.Task;

@Mapper
public interface TaskMapper {
    @Insert("INSERT INTO tasks (id, name, description, status) VALUES (#{id}, #{name}, #{description}, #{status})")
    void addTask(Task task);

    @Delete("DELETE FROM tasks WHERE id = #{id}")
    void deleteTaskById(UUID id);

    @Select("SELECT * FROM tasks WHERE id = #{id}")
    Task findById(UUID id);

    @Update("UPDATE tasks SET name=#{name}, description=#{description}, status=#{status} WHERE id=#{id}")
    void updateTask(Task task);
}

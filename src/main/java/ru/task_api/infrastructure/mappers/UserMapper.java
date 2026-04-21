package ru.task_api.infrastructure.mappers;

import java.util.UUID;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import ru.task_api.domain.entities.User;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (id, username, password) VALUES (#{id}, #{username}, #{password})")
    void addUser(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUserById(UUID id);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(UUID id);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);
}

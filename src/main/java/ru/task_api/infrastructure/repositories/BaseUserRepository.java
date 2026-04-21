package ru.task_api.infrastructure.repositories;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import ru.task_api.application.abstractions.persistence.repositories.UsersRepository;
import ru.task_api.domain.entities.User;
import ru.task_api.infrastructure.mappers.UserMapper;

@Repository
public class BaseUserRepository implements UsersRepository {

    private final UserMapper mapper;

    public BaseUserRepository(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void addUser(User user) {
        mapper.addUser(user);
    }

    @Override
    public void deleteUserById(UUID id) {
        mapper.deleteUserById(id);
    }

    @Override
    public User findById(UUID id) {   
        return mapper.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return mapper.findByUsername(username);
    }
}

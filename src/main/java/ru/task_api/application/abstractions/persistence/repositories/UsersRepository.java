package ru.task_api.application.abstractions.persistence.repositories;

import java.util.UUID;

import ru.task_api.domain.entities.User;

public interface UsersRepository {
    void addUser(User user);

    void deleteUserById(UUID id);

    User findById(UUID id);

    User findByUsername(String username);
}

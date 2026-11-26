package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(long id);

    User save(User user);

    void deleteById(long id);

    boolean emailExists(User user);
}
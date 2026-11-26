package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO saveUser(UserDTO userDto);
}
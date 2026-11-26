package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ConflictException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserDTO;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO saveUser(UserDTO userDto) {
        User user = UserMapper.mapToUser(userDto);
        if (repository.emailExists(user)) {
            throw new ConflictException("Email уже используется");
        }
        return UserMapper.mapToUserDto(repository.save(user));
    }

    public UserDTO getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + id));
        return UserMapper.mapToUserDto(user);
    }

    public UserDTO updateUser(Long id, UserDTO updates) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + id));

        if (updates.getName() != null && !updates.getName().isBlank()) {
            existing.setName(updates.getName());
        }
        if (updates.getEmail() != null && !updates.getEmail().isBlank()) {
            // проверить, что email не занят другим пользователем
            repository.findAll().stream()
                    .filter(u -> !u.getId().equals(id))
                    .filter(u -> u.getEmail().equalsIgnoreCase(updates.getEmail()))
                    .findAny()
                    .ifPresent(u -> {
                        throw new ConflictException("Почта уже используется");
                    });
            existing.setEmail(updates.getEmail());
        }
        return UserMapper.mapToUserDto(repository.save(existing));
    }

    public void deleteUser(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + id));
        repository.deleteById(id);
    }
}
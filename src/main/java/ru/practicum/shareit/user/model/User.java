package ru.practicum.shareit.user.model;

import lombok.Data;

@Data
@lombok.EqualsAndHashCode(of = {"email"})
public class User {
    private Long id;
    private String email;
    private String name;
}
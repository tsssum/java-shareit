package ru.practicum.shareit.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
public class Booking {
    Long id;
    @NotNull
    LocalDateTime start;
    @NotNull
    LocalDateTime end;
    Item item;
    User booker;

}

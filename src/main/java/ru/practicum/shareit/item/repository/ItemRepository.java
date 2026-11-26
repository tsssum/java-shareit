package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    List<Item> findByUserId(long userId);

    Optional<Item> findById(long id);

    Item save(Item item);

    void update(Long itemId, ItemDTO itemDTO);

    List<Item> search(String text);
}
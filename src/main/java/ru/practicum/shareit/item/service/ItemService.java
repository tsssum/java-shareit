package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    ItemDTO addItem(Long userId, ItemDTO itemDTO);

    ItemDTO updateItem(Long userId, Long itemId, ItemDTO itemDTO);

    ItemDTO getItem(Long itemId);

    List<ItemDTO> getItemsByOwner(Long userId);

    List<ItemDTO> search(String text);

}

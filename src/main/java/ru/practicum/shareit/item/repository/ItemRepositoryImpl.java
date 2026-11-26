package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> ITEMS = new HashMap<>();
    private long nextId = 1;

    @Override
    public List<Item> findByUserId(long userId) {
        return ITEMS.values().stream()
                .filter(item -> item.getOwner() != null
                        && item.getOwner().getId() != null
                        && item.getOwner().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(long id) {
        return Optional.ofNullable(ITEMS.get(id));
    }

    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(nextId++);
        }
        ITEMS.put(item.getId(), item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemDTO itemDTO) {
        Item item = ITEMS.get(itemId);
        if (itemDTO.getTitle() != null) {
            item.setTitle(itemDTO.getTitle());
        }
        if (itemDTO.getDescription() != null) {
            item.setDescription(itemDTO.getDescription());
        }
        if (itemDTO.getAvailable() != null) {
            item.setAvailable(itemDTO.getAvailable());
        }
    }

    // и когда будет работа с бд исправить поиск
    @Override
    public List<Item> search(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        String needle = text.toLowerCase();
        return ITEMS.values().stream()
                .filter(item -> Boolean.TRUE.equals(item.getAvailable()))
                .filter(item -> containsIgnoreCase(item.getTitle(), needle)
                        || containsIgnoreCase(item.getDescription(), needle))
                .collect(Collectors.toList());
    }

    private static boolean containsIgnoreCase(String field, String lowerNeedle) {
        return field != null && field.toLowerCase().contains(lowerNeedle);
    }
}

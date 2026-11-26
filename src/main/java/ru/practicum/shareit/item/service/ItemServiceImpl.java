package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ConflictException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;

    @Override
    public ItemDTO addItem(Long userId, ItemDTO itemDto) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + userId));
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(owner);
        return ItemMapper.mapToItemDto(repository.save(item));
    }

    // Изменить можно название, описание и статус доступа к аренде.
    // Редактировать вещь может только её владелец.
    @Override
    public ItemDTO updateItem(Long userId, Long itemId, ItemDTO itemDTO) {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь с id=" + itemId + " не найдена"));

        if (item.getOwner().getId().equals(userId)) {
            repository.update(itemId, itemDTO);
        } else {
            throw new NotFoundException("Редактировать вещь может только её владелец");
        }
        return ItemMapper.mapToItemDto(repository.save(item));
    }

    @Override
    public ItemDTO getItem(Long itemId) {
        Optional<Item> itemOptional = repository.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new NotFoundException("Вещь с id=" + itemId + " не найдена");
        }
        return ItemMapper.mapToItemDto(itemOptional.get());
    }

    // Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой из них
    @Override
    public List<ItemDTO> getItemsByOwner(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return repository.findByUserId(userId).stream()
                    .map(ItemMapper::mapToItemDto)
                    .collect(Collectors.toList());
        } else {
            throw new NotFoundException("Пользователь не найден: " + userId);
        }

    }

    // система ищет вещи, содержащие этот текст в названии или описании
    @Override
    public List<ItemDTO> search(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        return repository.search(text).stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }
}

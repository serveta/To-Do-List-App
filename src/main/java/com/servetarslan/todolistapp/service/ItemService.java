package com.servetarslan.todolistapp.service;

import com.servetarslan.todolistapp.dto.ItemCreateDto;
import com.servetarslan.todolistapp.dto.ItemDto;
import com.servetarslan.todolistapp.model.Item;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ItemService {
    ItemCreateDto createItem(Long userId, Long toDoListId, ItemCreateDto itemCreateDto);
    List<ItemDto> getAllItems(Long userId, Long toDoListId);
    ResponseEntity<ItemDto> getItemById(Long userId, Long toDoListId, Long itemId);
    ResponseEntity<ItemDto> updateItem(Long itemId, Long toDoListId, Long userId, ItemDto itemDto);
    ResponseEntity<Map<String,Boolean>> deleteItem(Long itemId, Long toDoListId, Long userId);

    ItemDto EntityToDto(Item item);
    Item DtoToEntity(ItemDto itemDto);
}

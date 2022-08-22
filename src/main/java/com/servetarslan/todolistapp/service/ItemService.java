package com.servetarslan.todolistapp.service;

import com.servetarslan.todolistapp.dto.ItemCreateDto;
import com.servetarslan.todolistapp.dto.ItemDto;

import java.util.List;
import java.util.Map;

public interface ItemService {
    ItemCreateDto createItem(Long userId, Long toDoListId, ItemCreateDto itemCreateDto);
    List<ItemDto> getAllItems(Long userId, Long toDoListId);
    ItemDto getItemDtoById(Long userId, Long toDoListId, Long itemId);
    ItemDto updateItem(Long itemId, Long toDoListId, Long userId, ItemDto itemDto);
    Map<String,Boolean> deleteItem(Long itemId, Long toDoListId, Long userId);
}

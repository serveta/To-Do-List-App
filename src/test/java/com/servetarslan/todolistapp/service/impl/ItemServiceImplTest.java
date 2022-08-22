package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.ItemCreateDto;
import com.servetarslan.todolistapp.dto.ItemDto;
import com.servetarslan.todolistapp.model.Item;
import com.servetarslan.todolistapp.model.ToDoList;
import com.servetarslan.todolistapp.model.User;
import com.servetarslan.todolistapp.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ToDoListServiceImpl toDoListServiceImpl;

    @Mock
    private ItemRepository itemRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void when_createItem_called_should_return_ItemCreateDto() {
        Long userId = 1L;
        Long toDoListId = 2L;
        Date date = new Date();
        ToDoList toDoList = new ToDoList("title", new User());
        ItemCreateDto itemCreateDto = new ItemCreateDto("test-title", "test-desc", date);
        Item item = new Item("test-title", "test-desc", date, toDoList);

        when(toDoListServiceImpl.getToDoListByIdAndUserIdIfExist(toDoListId, userId)).thenReturn(toDoList);
        when(itemRepository.save(item)).thenReturn(item);

        ItemCreateDto result = itemService.createItem(userId, toDoListId, itemCreateDto);

        assertEquals(itemCreateDto.getTitle(), result.getTitle());
        verify(itemRepository).save(item);
    }

    @Test
    public void when_getAllItems_called_should_return_ItemDtoList() {
        Long userId = 1L;
        Long toDoListId = 2L;
        ToDoList toDoList = new ToDoList("title", new User());
        Item item = new Item("test-title", "test-desc", new Date(), new ToDoList());

        when(toDoListServiceImpl.getToDoListByIdAndUserIdIfExist(toDoListId, userId)).thenReturn(toDoList);
        when(itemRepository.findAllByToDoListId(toDoListId)).thenReturn(Collections.singletonList(item));

        List<ItemDto> result = itemService.getAllItems(userId, toDoListId);

        assertEquals(item.getTitle(), result.get(0).getTitle());
        verify(itemRepository).findAllByToDoListId(toDoListId);
    }

    @Test
    public void when_getItemDtoById_called_should_return_ItemDto() {
        Long userId = 1L;
        Long toDoListId = 2L;
        Long itemId = 3L;
        ToDoList toDoList = new ToDoList("title", new User());
        Item item = new Item("test-title", "test-desc", new Date(), new ToDoList());

        when(toDoListServiceImpl.getToDoListByIdAndUserIdIfExist(toDoListId, userId)).thenReturn(toDoList);
        when(itemRepository.findByIdAndToDoListId(itemId, toDoListId)).thenReturn(Optional.of(item));

        ItemDto result = itemService.getItemDtoById(userId, toDoListId, itemId);

        assertEquals(item.getTitle(), result.getTitle());
        verify(itemRepository).findByIdAndToDoListId(itemId, toDoListId);
    }

    @Test
    public void when_updateItem_called_should_return_ItemDto() {
        Long userId = 1L;
        Long toDoListId = 2L;
        Long itemId = 3L;
        Date date = new Date();
        ToDoList toDoList = new ToDoList("title", new User());
        Item existItem = new Item("test", "test", date, toDoList);
        ItemDto itemDto = ItemDto
                .builder()
                .id(itemId)
                .title("update")
                .description("update")
                .expiration(date)
                .build();
        Item itemUpdate = new Item("update", "update", date, toDoList);

        when(toDoListServiceImpl.getToDoListByIdAndUserIdIfExist(toDoListId, userId)).thenReturn(toDoList);
        when(itemRepository.findByIdAndToDoListId(itemId, toDoListId)).thenReturn(Optional.of(existItem));
        when(itemRepository.save(existItem)).thenReturn(itemUpdate);

        ItemDto result = itemService.updateItem(userId, toDoListId, itemId , itemDto);

        assertEquals(itemDto.getTitle(), result.getTitle());
        verify(itemRepository).save(existItem);
    }

    @Test
    public void when_deleteItem_called_should_return_Map() {
        Long userId = 1L;
        Long toDoListId = 2L;
        Long itemId = 3L;
        ToDoList toDoList = new ToDoList("title", new User());
        Item item = new Item("test", "test", new Date(), toDoList);

        when(toDoListServiceImpl.getToDoListByIdAndUserIdIfExist(toDoListId, userId)).thenReturn(toDoList);
        when(itemRepository.findByIdAndToDoListId(itemId, toDoListId)).thenReturn(Optional.of(item));

        Map<String, Boolean> result = itemService.deleteItem(userId, toDoListId, itemId);

        assertEquals(Boolean.TRUE, result.get("deleted"));
        verify(itemRepository).delete(item);
    }
}

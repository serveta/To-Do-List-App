package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.ItemCreateDto;
import com.servetarslan.todolistapp.dto.ItemDto;
import com.servetarslan.todolistapp.dto.ToDoListDto;
import com.servetarslan.todolistapp.model.Item;
import com.servetarslan.todolistapp.model.ToDoList;
import com.servetarslan.todolistapp.model.User;
import com.servetarslan.todolistapp.repository.ItemRepository;
import com.servetarslan.todolistapp.service.ToDoListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ToDoListService toDoListService;

    @Mock
    private ItemRepository itemRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void when_createItem_called_should_return_ItemCreateDto() {
        Long userId = 1L;
        Long toDoListId = 2L;
        ToDoListDto toDoListDto = new ToDoListDto(toDoListId, "title");
        ToDoList toDoList = new ToDoList("title", new User());
        ItemCreateDto itemCreateDto = new ItemCreateDto("test-title", "test-desc", new Date());
        Item item = new Item("test-title", "test-desc", new Date(), toDoList);

        when(toDoListService.getToDoListById(userId, toDoListId)).thenReturn(ResponseEntity.ok(toDoListDto));
        when(toDoListService.getToDoListById(toDoListId)).thenReturn(toDoList);
        when(itemRepository.save(item)).thenReturn(item);

        ItemCreateDto result = itemService.createItem(userId, toDoListId, itemCreateDto);

        assertEquals(itemCreateDto.getTitle(), result.getTitle());
        verify(itemRepository).save(item);
    }

    @Test
    public void when_getAllItems_called_should_return_ItemDtoList() {
        Long userId = 1L;
        Long toDoListId = 2L;
        ToDoListDto toDoListDto = new ToDoListDto(toDoListId, "title");
        Item item = new Item("test-title", "test-desc", new Date(), new ToDoList());

        when(toDoListService.getToDoListById(userId, toDoListId)).thenReturn(ResponseEntity.ok(toDoListDto));
        when(itemRepository.findAllByToDoListId(toDoListId)).thenReturn(Collections.singletonList(item));

        List<ItemDto> result = itemService.getAllItems(userId, toDoListId);

        assertEquals(item.getTitle(), result.get(0).getTitle());
        verify(itemRepository).findAllByToDoListId(toDoListId);
    }

    @Test
    public void when_getItemById_called_should_return_ResponseEntity_ItemDto() {
        Long userId = 1L;
        Long toDoListId = 2L;
        Long itemId = 3L;
        ToDoListDto toDoListDto = new ToDoListDto(toDoListId, "title");
        Item item = new Item("test-title", "test-desc", new Date(), new ToDoList());

        when(toDoListService.getToDoListById(userId, toDoListId)).thenReturn(ResponseEntity.ok(toDoListDto));
        when(itemRepository.findByIdAndToDoListId(itemId, toDoListId)).thenReturn(item);

        ResponseEntity<ItemDto> result = itemService.getItemById(userId, toDoListId, itemId);

        assertEquals(result.getStatusCodeValue(), 200);
        assertEquals(item.getTitle(), Objects.requireNonNull(result.getBody()).getTitle());
        verify(itemRepository).findByIdAndToDoListId(itemId, toDoListId);
    }

    @Test
    public void when_updateItem_called_should_return_ResponseEntity_ItemDto() {
        Long userId = 1L;
        Long toDoListId = 2L;
        Long itemId = 3L;
        Date date = new Date();
        ToDoList toDoList = new ToDoList("title", new User());
        ToDoListDto toDoListDto = new ToDoListDto(toDoListId, "title");

        Item itemWillUpdate = new Item("test", "test", date, toDoList);
        Item item = new Item("update", "update", date, toDoList);
        ItemDto itemDto = ItemDto
                .builder()
                .id(itemId)
                .title("update")
                .description("update")
                .expiration(date)
                .build();
        Item itemUpdate = new Item("update", "update", date, toDoList);

        when(toDoListService.getToDoListById(userId,toDoListId)).thenReturn(ResponseEntity.ok(toDoListDto));
        when(itemRepository.findByIdAndToDoListId(itemId, toDoListId)).thenReturn(itemWillUpdate);
        when(toDoListService.getToDoListById(toDoListId)).thenReturn(toDoList);
        when(itemRepository.save(item)).thenReturn(itemUpdate);

        ResponseEntity<ItemDto> result = itemService.updateItem(itemId, toDoListId, userId, itemDto);

        assertEquals(itemDto.getTitle(), Objects.requireNonNull(result.getBody()).getTitle());
        assertEquals(result.getStatusCodeValue(), 200);
        verify(itemRepository).save(item);
    }

    @Test
    public void when_deleteItem_called_should_return_ResponseEntity_Map_Boolean() {
        Long userId = 1L;
        Long toDoListId = 2L;
        Long itemId = 3L;

        Item item = new Item();

        when(toDoListService.getToDoListById(userId,toDoListId)).thenReturn(ResponseEntity.ok(new ToDoListDto()));
        when(itemRepository.findByIdAndToDoListId(itemId, toDoListId)).thenReturn(item);

        ResponseEntity<Map<String, Boolean>> result = itemService.deleteItem(itemId, toDoListId, userId);

        assertNotNull(result);
        assertEquals(Boolean.TRUE, Objects.requireNonNull(result.getBody()).get("deleted"));
        assertEquals(result.getStatusCodeValue(), 200);
        verify(itemRepository).delete(item);
    }
}

package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.ToDoListCreateDto;
import com.servetarslan.todolistapp.dto.ToDoListDto;
import com.servetarslan.todolistapp.model.ToDoList;
import com.servetarslan.todolistapp.model.User;
import com.servetarslan.todolistapp.repository.ToDoListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoListServiceImplTest {
    @InjectMocks
    private ToDoListServiceImpl toDoListService;

    @Mock
    private ToDoListRepository toDoListRepository;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void when_createToDoList_called_should_return_ToDoListCreateDto() {
        Long userId = 1L;
        User user = new User();
        ToDoListCreateDto toDoListCreateDto = new ToDoListCreateDto("Test-title");
        ToDoList toDoList = new ToDoList("Test-title", user);

        when(userServiceImpl.getUserByIdIfExist(userId)).thenReturn(user);
        when(toDoListRepository.save(toDoList)).thenReturn(toDoList);

        ToDoListCreateDto result = toDoListService.createToDoList(userId, toDoListCreateDto);

        assertEquals(toDoListCreateDto.getTitle(), result.getTitle());
        verify(toDoListRepository).save(toDoList);
    }

    @Test
    public void when_getAllToDoLists_called_should_return_ToDoListDtoList() {
        Long userId = 1L;
        User user = new User();
        ToDoList toDoList1 = new ToDoList("test-1", new User());
        ToDoList toDoList2 = new ToDoList("test-2", new User());
        List<ToDoList> list = new ArrayList<>();
        list.add(toDoList1);
        list.add(toDoList2);

        when(userServiceImpl.getUserByIdIfExist(userId)).thenReturn(user);
        when(toDoListRepository.findAllByUserId(userId)).thenReturn(Collections.synchronizedList(list));

        List<ToDoListDto> result = toDoListService.getAllToDoLists(userId);

        assertNotNull(result);
        assertEquals(list.get(0).getTitle(), result.get(0).getTitle());
        verify(toDoListRepository).findAllByUserId(userId);
    }

    @Test
    public void when_getToDoListDtoById_called_should_return_ToDoListDto() {
        Long userId = 1L;
        Long toDoListId = 2L;

        ToDoList toDoList = new ToDoList("test-1", new User());

        when(toDoListRepository.findByIdAndUserId(toDoListId, userId)).thenReturn(Optional.of(toDoList));

        ToDoListDto result = toDoListService.getToDoListDtoById(userId, toDoListId);

        assertEquals(toDoList.getTitle(), result.getTitle());
        verify(toDoListRepository).findByIdAndUserId(toDoListId, userId);
    }

    @Test
    public void when_updateToDoList_called_should_return_ToDoListDto() {
        Long userId = 1L;
        Long toDoListId = 2L;
        ToDoList existToDoList = new ToDoList("test-1", new User());
        ToDoListDto toDoListDto = new ToDoListDto(toDoListId, "update-test");
        ToDoList toDoListUpdate = new ToDoList("update-test", new User());

        when(toDoListRepository.findByIdAndUserId(toDoListId, userId)).thenReturn(Optional.of(existToDoList));
        when(toDoListRepository.save(existToDoList)).thenReturn(toDoListUpdate);

        ToDoListDto result = toDoListService.updateToDoList(userId, toDoListId, toDoListDto);

        assertEquals(toDoListDto.getTitle(), result.getTitle());
        verify(toDoListRepository).save(existToDoList);
    }

    @Test
    public void when_deleteToDoList_called_should_return_Map() {
        Long userId = 1L;
        Long toDoListId = 2L;

        ToDoList toDoList = new ToDoList("test-1", new User());

        when(toDoListRepository.findByIdAndUserId(toDoListId, userId)).thenReturn(Optional.of(toDoList));

        Map<String, Boolean> result = toDoListService.deleteToDoList(userId, toDoListId);

        assertEquals(Boolean.TRUE, result.get("deleted"));
        verify(toDoListRepository).delete(toDoList);
    }
}

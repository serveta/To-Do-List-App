package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.ToDoListCreateDto;
import com.servetarslan.todolistapp.dto.ToDoListDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.model.Role;
import com.servetarslan.todolistapp.model.ToDoList;
import com.servetarslan.todolistapp.model.User;
import com.servetarslan.todolistapp.repository.ToDoListRepository;
import com.servetarslan.todolistapp.service.UserService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoListServiceImplTest {
    @InjectMocks
    private ToDoListServiceImpl toDoListService;

    @Mock
    private ToDoListRepository toDoListRepository;

    @Mock
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void when_createToDoList_called_should_return_ToDoListCreateDto() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        User user = new User();
        ToDoListCreateDto toDoListCreateDto = new ToDoListCreateDto("Test-title");
        ToDoList toDoList = new ToDoList("Test-title", user);

        when(userService.getUserById(userId)).thenReturn(ResponseEntity.ok(userDto));
        when(userService.getUserByIdReturnUser(userId)).thenReturn(user);
        when(toDoListRepository.save(toDoList)).thenReturn(toDoList);

        ToDoListCreateDto result = toDoListService.createToDoList(userId, toDoListCreateDto);

        assertNotNull(result);
        assertEquals(toDoListCreateDto.getTitle(), result.getTitle());
        verify(toDoListRepository).save(toDoList);
    }

    @Test
    public void when_getAllToDoLists_called_should_return_ToDoListDtoList() {
        Long userId = 1L;
        ToDoList toDoList1 = new ToDoList("test-1", new User());
        ToDoList toDoList2 = new ToDoList("test-2", new User());
        List<ToDoList> list = new ArrayList<>();
        list.add(toDoList1);
        list.add(toDoList2);

        when(toDoListRepository.findAllByUserId(userId)).thenReturn(Collections.synchronizedList(list));

        List<ToDoListDto> result = toDoListService.getAllToDoLists(userId);

        assertNotNull(result);
        assertEquals(list.get(0).getTitle(), result.get(0).getTitle());
        verify(toDoListRepository).findAllByUserId(userId);
    }

    @Test
    public void when_getToDoListById_called_should_return_ResponseEntity_ToDoListDto() {
        Long userId = 1L;
        Long toDoListId = 2L;

        ToDoList toDoList = new ToDoList("test-1", new User());

        when(toDoListRepository.findByIdAndUserId(toDoListId, userId)).thenReturn(Optional.of(toDoList));

        ResponseEntity<ToDoListDto> result = toDoListService.getToDoListById(userId, toDoListId);

        assertEquals(toDoList.getTitle(), Objects.requireNonNull(result.getBody()).getTitle());
        verify(toDoListRepository).findByIdAndUserId(toDoListId, userId);
    }

    @Test
    public void when_getToDoListById_called_should_return_ToDoList() {
        Long toDoListId = 2L;
        ToDoList toDoList = new ToDoList("test", new User());

        when(toDoListRepository.findById(toDoListId)).thenReturn(Optional.of(toDoList));

        ToDoList result = toDoListService.getToDoListById(toDoListId);

        assertEquals(toDoList.getTitle(), result.getTitle());
        verify(toDoListRepository).findById(toDoListId);
    }

    @Test
    public void when_updateToDoList_called_should_return_ResponseEntity_ToDoListDto() {
        Long userId = 1L;
        Long toDoListId = 2L;
        ToDoList toDoList = new ToDoList("test-1", new User());
        ToDoListDto toDoListDto = new ToDoListDto(toDoListId, "update-test");
        ToDoList toDoListUpdate = new ToDoList("update-test", new User());

        when(toDoListRepository.findByIdAndUserId(userId, toDoListId)).thenReturn(Optional.of(toDoList));
        when(toDoListRepository.save(toDoList)).thenReturn(toDoListUpdate);

        ResponseEntity<ToDoListDto> result = toDoListService.updateToDoList(toDoListId, userId, toDoListDto);

        assertEquals(toDoListDto.getTitle(), Objects.requireNonNull(result.getBody()).getTitle());
        verify(toDoListRepository).save(toDoList);
    }

    @Test
    public void when_deleteToDoList_called_should_return_ResponseEntity_Map_Boolean(){
        Long userId = 1L;
        Long toDoListId = 2L;

        ToDoList toDoList = new ToDoList("test-1", new User());

        when(toDoListRepository.findByIdAndUserId(userId, toDoListId)).thenReturn(Optional.of(toDoList));

        ResponseEntity<Map<String, Boolean>> result = toDoListService.deleteToDoList(toDoListId, userId);

        assertNotNull(result);
        assertEquals(Boolean.TRUE, Objects.requireNonNull(result.getBody()).get("deleted"));
        assertEquals(result.getStatusCodeValue(), 200);
        verify(toDoListRepository).delete(toDoList);
    }
}

package com.servetarslan.todolistapp.service;

import com.servetarslan.todolistapp.dto.ToDoListCreateDto;
import com.servetarslan.todolistapp.dto.ToDoListDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.model.ToDoList;
import com.servetarslan.todolistapp.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ToDoListService {
    ToDoListCreateDto createToDoList(Long userId, ToDoListCreateDto toDoListCreateDto);
    List<ToDoListDto> getAllToDoLists(Long userId);
    ResponseEntity<ToDoListDto> getToDoListById(Long userId, Long toDoListId);
    ToDoList getToDoListById(Long toDoListId);
    ResponseEntity<ToDoListDto> updateToDoList(Long toDoListId, Long userId, ToDoListDto toDoListDto);
    ResponseEntity<Map<String,Boolean>> deleteToDoList(Long toDoListId, Long userId);
}

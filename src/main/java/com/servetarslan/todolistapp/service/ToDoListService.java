package com.servetarslan.todolistapp.service;

import com.servetarslan.todolistapp.dto.ToDoListCreateDto;
import com.servetarslan.todolistapp.dto.ToDoListDto;

import java.util.List;
import java.util.Map;

public interface ToDoListService {
    ToDoListCreateDto createToDoList(Long userId, ToDoListCreateDto toDoListCreateDto);
    List<ToDoListDto> getAllToDoLists(Long userId);
    ToDoListDto getToDoListDtoById(Long userId, Long toDoListId);
    ToDoListDto updateToDoList(Long toDoListId, Long userId, ToDoListDto toDoListDto);
    Map<String,Boolean> deleteToDoList(Long toDoListId, Long userId);
}

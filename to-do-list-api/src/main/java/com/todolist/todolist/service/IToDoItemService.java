package com.todolist.todolist.service;

import com.todolist.todolist.dto.ToDoItemDto;
import com.todolist.todolist.model.ToDoItem;

import java.util.List;

public interface IToDoItemService {

    List<ToDoItem> findAll();

    ToDoItem findToDoItem(int id);

    ToDoItem saveToDoItem(int toDoListId, ToDoItemDto toDoItemDto);

    ToDoItem updateToDoItem(int id, ToDoItemDto toDoItemDto);

    ToDoItem deleteToDoItem(int id);
}

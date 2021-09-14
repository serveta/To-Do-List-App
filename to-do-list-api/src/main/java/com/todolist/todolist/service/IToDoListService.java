package com.todolist.todolist.service;

import com.todolist.todolist.dto.ToDoListDto;
import com.todolist.todolist.model.ToDoList;

import java.util.List;

public interface IToDoListService {

    List<ToDoList> findAll();

    ToDoList findToDoList(int id);

    ToDoList saveToDoList(ToDoListDto toDoListDto);

    ToDoList updateToDoList(int id, ToDoListDto toDoListDto);

    ToDoList deleteToDoList(int id);
}

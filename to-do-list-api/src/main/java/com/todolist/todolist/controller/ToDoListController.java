package com.todolist.todolist.controller;

import com.todolist.todolist.dto.ToDoListDto;
import com.todolist.todolist.model.ToDoList;
import com.todolist.todolist.service.ToDoListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/to-do-lists")
public class ToDoListController {

    private final ToDoListService toDoListService;

    public ToDoListController(ToDoListService toDoListService){
        this.toDoListService = toDoListService;
    }

    @GetMapping
    List<ToDoList> getAllToDoList(){
        return toDoListService.findAll();
    }

    @GetMapping("/{id}")
    ToDoList one(@PathVariable int id){
        return toDoListService.findToDoList(id);
    }

    @PostMapping("/save")
    ToDoList saveToDoList(@RequestBody ToDoListDto toDoListDto){
        return toDoListService.saveToDoList(toDoListDto);
    }

    @PutMapping("/{id}")
    ToDoList updateToDoList(@PathVariable int id,
                            @RequestBody ToDoListDto toDoListDto){
        return toDoListService.updateToDoList(id, toDoListDto);
    }

    @DeleteMapping("/{id}")
    ToDoList deleteToDoList(@PathVariable int id) {
        return toDoListService.deleteToDoList(id);
    }

}

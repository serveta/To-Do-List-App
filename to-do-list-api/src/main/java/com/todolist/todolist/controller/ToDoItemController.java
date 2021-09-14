package com.todolist.todolist.controller;

import com.todolist.todolist.dto.ToDoItemDto;
import com.todolist.todolist.model.ToDoItem;
import com.todolist.todolist.service.ToDoItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/to-do-items")
public class ToDoItemController {
    private final ToDoItemService toDoItemService;

    public ToDoItemController(ToDoItemService toDoItemService){
        this.toDoItemService = toDoItemService;
    }

    @GetMapping
    List<ToDoItem> getAllToDoItem(){
        return toDoItemService.findAll();
    }

    @GetMapping("/{id}")
    ToDoItem getOneToDoItem(@PathVariable int id){
        return toDoItemService.findToDoItem(id);
    }

    @PostMapping("/{toDoListId}/item")
    ToDoItem saveToDoItem(@PathVariable int toDoListId,
                          @RequestBody ToDoItemDto toDoItemDto){
        return toDoItemService.saveToDoItem(toDoListId, toDoItemDto);
    }

    @PutMapping("/{id}")
    ToDoItem updateToDoItem(@PathVariable int id,
                            @RequestBody ToDoItemDto toDoItemDto){
        return toDoItemService.updateToDoItem(id, toDoItemDto);
    }

    @DeleteMapping("/{id}")
    ToDoItem deleteToDoItem(@PathVariable int id){
        return toDoItemService.deleteToDoItem(id);
    }
}

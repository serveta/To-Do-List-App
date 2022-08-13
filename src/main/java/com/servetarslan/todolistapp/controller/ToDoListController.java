package com.servetarslan.todolistapp.controller;

import com.servetarslan.todolistapp.dto.ToDoListCreateDto;
import com.servetarslan.todolistapp.dto.ToDoListDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoListController {

    @Autowired
    private ToDoListService toDoListService;

    // SAVE
    // http://localhost:8080/api/v1/users/1/todolists
    @PostMapping("/users/{userId}/todolists")
    public ToDoListCreateDto createToDoList(@PathVariable Long userId, @RequestBody ToDoListCreateDto toDoListCreateDto) {
        toDoListService.createToDoList(userId, toDoListCreateDto);
        return toDoListCreateDto;
    }

    // GET ALL (LIST)
    // http://localhost:8080/api/v1/users/1/todolists
    @GetMapping("/users/{userId}/todolists")
    public List<ToDoListDto> getAllToDoLists(@RequestParam Long userId) {
        return toDoListService.getAllToDoLists(userId);
    }

    @GetMapping("users/{userId}/todolists/{toDoListId}")
    public ResponseEntity<ToDoListDto> getToDoListById(@PathVariable Long userId, @PathVariable Long toDoListId) {
        return toDoListService.getToDoListById(userId, toDoListId);
    }

    @PutMapping("users/{userId}/todolists/{toDoListId}")
    public ResponseEntity<ToDoListDto> updateToDoList(@PathVariable Long userId, @PathVariable Long toDoListId, @RequestBody ToDoListDto toDoListDto) {
        return toDoListService.updateToDoList(userId, toDoListId, toDoListDto);
    }

    @DeleteMapping("users/{userId}/todolists/{toDoListId}")
    public ResponseEntity<Map<String,Boolean>> deleteToDoList(@PathVariable Long userId, @PathVariable Long toDoListId) {
        return toDoListService.deleteToDoList(userId, toDoListId);
    }
}

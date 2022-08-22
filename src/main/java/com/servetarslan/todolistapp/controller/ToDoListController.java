package com.servetarslan.todolistapp.controller;

import com.servetarslan.todolistapp.dto.ToDoListCreateDto;
import com.servetarslan.todolistapp.dto.ToDoListDto;
import com.servetarslan.todolistapp.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoListController {
    @Autowired
    private ToDoListService toDoListService;

    @PostMapping("/users/{userId}/todolists")
    public ResponseEntity<ToDoListCreateDto> createToDoList(@PathVariable Long userId, @RequestBody ToDoListCreateDto toDoListCreateDto) {
        toDoListService.createToDoList(userId, toDoListCreateDto);
        return ResponseEntity.ok(toDoListCreateDto);
    }

    @GetMapping("/users/{userId}/todolists")
    public ResponseEntity<List<ToDoListDto>> getAllToDoLists(@PathVariable Long userId) {
        return ResponseEntity.ok(toDoListService.getAllToDoLists(userId));
    }

    @GetMapping("users/{userId}/todolists/{toDoListId}")
    public ResponseEntity<ToDoListDto> getToDoListById(@PathVariable Long userId, @PathVariable Long toDoListId) {
        return ResponseEntity.ok(toDoListService.getToDoListDtoById(userId, toDoListId));
    }

    @PutMapping("users/{userId}/todolists/{toDoListId}")
    public ResponseEntity<ToDoListDto> updateToDoList(@PathVariable Long userId, @PathVariable Long toDoListId, @RequestBody ToDoListDto toDoListDto) {
        return ResponseEntity.ok(toDoListService.updateToDoList(userId, toDoListId, toDoListDto));
    }

    @DeleteMapping("users/{userId}/todolists/{toDoListId}")
    public ResponseEntity<Map<String, Boolean>> deleteToDoList(@PathVariable Long userId, @PathVariable Long toDoListId) {
        return ResponseEntity.ok(toDoListService.deleteToDoList(userId, toDoListId));
    }
}

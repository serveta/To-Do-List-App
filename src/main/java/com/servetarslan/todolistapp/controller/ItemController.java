package com.servetarslan.todolistapp.controller;

import com.servetarslan.todolistapp.dto.ItemCreateDto;
import com.servetarslan.todolistapp.dto.ItemDto;
import com.servetarslan.todolistapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/users/{userId}/todolists/{toDoListId}/items")
    public ItemCreateDto createItem(@PathVariable Long userId, @PathVariable Long toDoListId, @RequestBody ItemCreateDto itemCreateDto) {
        itemService.createItem(userId, toDoListId, itemCreateDto);
        return itemCreateDto;
    }

    @GetMapping("/users/{userId}/todolists/{toDoListId}/items")
    public List<ItemDto> getAllItems(@PathVariable Long userId, @PathVariable Long toDoListId) {
        return itemService.getAllItems(userId, toDoListId);
    }

    @GetMapping("/users/{userId}/todolists/{toDoListId}/items/{itemId}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable Long userId, @PathVariable Long toDoListId, @PathVariable Long itemId) {
        return itemService.getItemById(userId, toDoListId, itemId);
    }

    @PutMapping("/users/{userId}/todolists/{toDoListId}/items/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long userId, @PathVariable Long toDoListId, @PathVariable Long itemId, @RequestBody ItemDto itemDto) {
        return itemService.updateItem(itemId, toDoListId, userId, itemDto);
    }

    @DeleteMapping("/users/{userId}/todolists/{toDoListId}/items/{itemId}")
    public ResponseEntity<Map<String,Boolean>> deleteItem(@PathVariable Long userId, @PathVariable Long toDoListId, @PathVariable Long itemId) {
        return itemService.deleteItem(itemId, toDoListId, userId);
    }
}

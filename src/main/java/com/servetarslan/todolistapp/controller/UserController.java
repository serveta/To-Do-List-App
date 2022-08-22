package com.servetarslan.todolistapp.controller;

import com.servetarslan.todolistapp.dto.UserCreateDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserCreateDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        return ResponseEntity.ok(userService.createUser(userCreateDto));
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserDto> getUserDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDtoById(id));
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}

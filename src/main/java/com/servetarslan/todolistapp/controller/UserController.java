package com.servetarslan.todolistapp.controller;

import com.servetarslan.todolistapp.dto.UserCreateOrUpdateDto;
import com.servetarslan.todolistapp.dto.UserDto;
import com.servetarslan.todolistapp.service.UserService;
import lombok.SneakyThrows;
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

    // GET ALL (LIST)
    // http://localhost:8080/api/v1/users
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // SAVE
    // http://localhost:8080/api/v1/users
    @PostMapping("/users")
    public UserCreateOrUpdateDto createEmployee(@RequestBody UserCreateOrUpdateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return userCreateDto;
    }

    // FIND BY ID
    // http://localhost:8080/api/v1/users/5
    @SneakyThrows
    @GetMapping("users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // UPDATE
    // http://localhost:8080/api/v1/users/5
    @PutMapping("users/{id}")
    public ResponseEntity<UserCreateOrUpdateDto> updateUser(@PathVariable Long id, @RequestBody UserCreateOrUpdateDto userCreateDto) {
        return userService.updateUser(id, userCreateDto);
    }

    // DELETE
    // http://localhost:8080/api/v1/users/1
    @DeleteMapping("users/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}

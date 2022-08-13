package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.ToDoListCreateDto;
import com.servetarslan.todolistapp.dto.ToDoListDto;
import com.servetarslan.todolistapp.exception.ResourceNotFoundException;
import com.servetarslan.todolistapp.model.ToDoList;
import com.servetarslan.todolistapp.repository.ToDoListRepository;
import com.servetarslan.todolistapp.service.ToDoListService;
import com.servetarslan.todolistapp.service.UserService;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ToDoListServiceImpl implements ToDoListService {
    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ToDoListCreateDto createToDoList(Long userId, ToDoListCreateDto toDoListCreateDto) {
        try {
            if(userService.getUserById(userId) != null) {
                ToDoList toDoList = DtoToEntity(toDoListCreateDto);
                toDoList.setUser(userService.getUserByIdReturnUser(userId));
                toDoListRepository.save(toDoList);
                return toDoListCreateDto;
            }
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<ToDoListDto> getAllToDoLists(Long userId) {
        List<ToDoListDto> toDoListDtoList = new ArrayList<>();
        Iterable<ToDoList> toDoListIterable = toDoListRepository.findAllByUserId(userId);
        for (ToDoList toDoList : toDoListIterable) {
            ToDoListDto toDoListDto = EntityToDto(toDoList);
            toDoListDtoList.add(toDoListDto);
        }
        return toDoListDtoList;
    }

    @SneakyThrows
    @Override
    public ResponseEntity<ToDoListDto> getToDoListById(Long userId, Long toDoListId) {
        ToDoList toDoList = toDoListRepository.findByIdAndUserId(toDoListId, userId).orElseThrow(
                ()-> new ResourceNotFoundException("To-Do List does not found!"));
        ToDoListDto toDoListDto = EntityToDto(toDoList);
        return ResponseEntity.ok(toDoListDto);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<ToDoListDto> updateToDoList(Long toDoListId, Long userId, ToDoListDto toDoListDto) {
        ToDoList toDoListEntity = DtoToEntity(toDoListDto);

        ToDoList toDoList = toDoListRepository.findByIdAndUserId(userId, toDoListId)
                .orElseThrow(()->new ResourceNotFoundException("To-Do List does not found!"));

        toDoList.setTitle(toDoListEntity.getTitle());

        ToDoList toDoListUpdate = toDoListRepository.save(toDoList);
        ToDoListDto responseToDoListDto = EntityToDto(toDoListUpdate);
        return ResponseEntity.ok(responseToDoListDto);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Map<String, Boolean>> deleteToDoList(Long toDoListId, Long userId) {
        ToDoList toDoList = toDoListRepository.findByIdAndUserId(userId, toDoListId)
                .orElseThrow(()->new ResourceNotFoundException("To-Do List does not found!"));
        toDoListRepository.delete(toDoList);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @Override
    public ToDoListDto EntityToDto(ToDoList toDoList) {
        return modelMapper.map(toDoList, ToDoListDto.class);
    }

    @Override
    public ToDoList DtoToEntity(ToDoListDto toDoListDto) {
        return modelMapper.map(toDoListDto, ToDoList.class);
    }

    public ToDoList DtoToEntity(ToDoListCreateDto toDoListCreateDto) {
        return modelMapper.map(toDoListCreateDto, ToDoList.class);
    }
}

package com.servetarslan.todolistapp.service.impl;

import com.servetarslan.todolistapp.dto.ToDoListCreateDto;
import com.servetarslan.todolistapp.dto.ToDoListDto;
import com.servetarslan.todolistapp.exception.ResourceNotFoundException;
import com.servetarslan.todolistapp.model.ToDoList;
import com.servetarslan.todolistapp.model.User;
import com.servetarslan.todolistapp.repository.ToDoListRepository;
import com.servetarslan.todolistapp.service.ToDoListService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ToDoListCreateDto createToDoList(Long userId, ToDoListCreateDto toDoListCreateDto) {
        User user = userServiceImpl.getUserByIdIfExist(userId);
        ToDoList toDoList = DtoToEntity(toDoListCreateDto);
        toDoList.setUser(user);
        ToDoList response = toDoListRepository.save(toDoList);
        return EntityToCreateDto(response);
    }

    @Override
    public List<ToDoListDto> getAllToDoLists(Long userId) {
        userServiceImpl.getUserByIdIfExist(userId);
        List<ToDoListDto> toDoListDtoList = new ArrayList<>();
        Iterable<ToDoList> toDoListIterable = toDoListRepository.findAllByUserId(userId);
        for (ToDoList toDoList : toDoListIterable) {
            ToDoListDto toDoListDto = EntityToDto(toDoList);
            toDoListDtoList.add(toDoListDto);
        }
        return toDoListDtoList;
    }

    @Override
    public ToDoListDto getToDoListDtoById(Long userId, Long toDoListId) {
        ToDoList toDoList = getToDoListByIdAndUserIdIfExist(toDoListId, userId);
        return EntityToDto(toDoList);
    }

    @Override
    public ToDoListDto updateToDoList(Long userId, Long toDoListId, ToDoListDto toDoListDto) {
        ToDoList existToDoList = getToDoListByIdAndUserIdIfExist(toDoListId, userId);

        ToDoList toDoList = DtoToEntity(toDoListDto);

        existToDoList.setTitle(toDoList.getTitle());

        ToDoList toDoListUpdate = toDoListRepository.save(existToDoList);
        return EntityToDto(toDoListUpdate);
    }

    @Override
    public Map<String, Boolean> deleteToDoList(Long userId, Long toDoListId) {
        ToDoList toDoList = getToDoListByIdAndUserIdIfExist(toDoListId, userId);
        toDoListRepository.delete(toDoList);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    protected ToDoList getToDoListByIdAndUserIdIfExist(Long toDoListId, Long userId) {
        return toDoListRepository.findByIdAndUserId(toDoListId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("To-Do list does not found!"));
    }

    private ToDoListDto EntityToDto(ToDoList toDoList) {
        return modelMapper.map(toDoList, ToDoListDto.class);
    }

    private ToDoListCreateDto EntityToCreateDto(ToDoList toDoList) {
        return modelMapper.map(toDoList, ToDoListCreateDto.class);
    }
    private ToDoList DtoToEntity(ToDoListDto toDoListDto) {
        return modelMapper.map(toDoListDto, ToDoList.class);
    }

    private ToDoList DtoToEntity(ToDoListCreateDto toDoListCreateDto) {
        return modelMapper.map(toDoListCreateDto, ToDoList.class);
    }
}

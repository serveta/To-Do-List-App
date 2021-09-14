package com.todolist.todolist.service;

import com.todolist.todolist.dto.ToDoListDto;
import com.todolist.todolist.model.ToDoList;
import com.todolist.todolist.model.User;
import com.todolist.todolist.repository.ToDoListRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListService implements IToDoListService{

    private final ToDoListRepository toDoListRepository;

    private final UserService userService;

    public ToDoListService(ToDoListRepository toDoListRepository, UserService userService){
        this.toDoListRepository = toDoListRepository;
        this.userService = userService;
    }

    public List<ToDoList> findAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = userService.findUserByEmail(authentication.getName());

        return userService.findUserById(userDetails.getId()).getToDoLists();
        //  toDoListRepository.findAll();
        //return toDoListRepository.findByUserId(userDetails);
    }

    public ToDoList findToDoList(int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = userService.findUserByEmail(authentication.getName());
        //return userService.findUserById(userDetails.getId()).getToDoLists().get(id); // null
        return toDoListRepository.findByIdAndUserId(id, userDetails.getId());
    }

    public ToDoList saveToDoList(ToDoListDto toDoListDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = userService.findUserByEmail(authentication.getName());

        if (userDetails != null){
            ToDoList newToDoList = new ToDoList();
            newToDoList.setName(toDoListDto.getName());
            newToDoList.setExpirationDate(toDoListDto.getExpirationDate());
            newToDoList.setUser(userDetails);
            return toDoListRepository.save(newToDoList);
        }
        else{
            return null;
        }
    }

    public ToDoList updateToDoList(int id, ToDoListDto toDoListDto){
        ToDoList todolist = findToDoList(id);
        if (todolist != null){
            todolist.setName(toDoListDto.getName());
            todolist.setExpirationDate(toDoListDto.getExpirationDate());
            toDoListRepository.save(todolist);
    }
        return todolist;
    }

    public ToDoList deleteToDoList(int id){
        ToDoList todolist = findToDoList(id);
        if (todolist != null){
            toDoListRepository.deleteById(todolist.getId());
        }
        return todolist;
    }

}

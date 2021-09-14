package com.todolist.todolist.service;

import com.todolist.todolist.dto.ToDoItemDto;
import com.todolist.todolist.exception.ToDoItemNotFoundException;
import com.todolist.todolist.model.ToDoItem;
import com.todolist.todolist.model.ToDoList;
import com.todolist.todolist.repository.ToDoItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoItemService implements IToDoItemService {

    private final ToDoItemRepository toDoItemRepository;
    private final ToDoListService toDoListService;

    public ToDoItemService(ToDoItemRepository toDoItemRepository,
                           ToDoListService toDoListService) {
        this.toDoItemRepository = toDoItemRepository;
        this.toDoListService = toDoListService;
    }

    public List<ToDoItem> findAll() {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //User userDetails = userService.findUserByEmail(authentication.getName());

        return toDoItemRepository.findAll();
    }

    public ToDoItem findToDoItem(int id) {
        return toDoItemRepository.findById(id)
                .orElseThrow(()->new ToDoItemNotFoundException("To-Do-Item could not find by id: " + id));
    }

    public ToDoItem saveToDoItem(int toDoListId, ToDoItemDto toDoItemDto) {
        ToDoList todolist = toDoListService.findToDoList(toDoListId);
        if (todolist != null){
            ToDoItem newToDoItem = new ToDoItem();
            newToDoItem.setName(toDoItemDto.getName());
            newToDoItem.setDescription(toDoItemDto.getDescription());
            newToDoItem.setStartingDate(toDoItemDto.getStartingDate());
            newToDoItem.setDeadline(toDoItemDto.getDeadline());
            newToDoItem.setStatus(toDoItemDto.getStatus());
            newToDoItem.setToDoList(todolist);
            return toDoItemRepository.save(newToDoItem);
        }
        else{
            return null;
        }
    }

    public ToDoItem updateToDoItem(int id, ToDoItemDto toDoItemDto) {
        ToDoItem todoitem = findToDoItem(id);
        if (todoitem != null){
            todoitem.setName(toDoItemDto.getName());
            todoitem.setDescription(toDoItemDto.getDescription());
            todoitem.setStartingDate(toDoItemDto.getStartingDate());
            todoitem.setDeadline(toDoItemDto.getDeadline());
            todoitem.setStatus(toDoItemDto.getStatus());
            toDoItemRepository.save(todoitem);
        }
        return todoitem;
    }

    public ToDoItem deleteToDoItem(int id) {
        ToDoItem todoitem = findToDoItem(id);
        if (todoitem != null){
            toDoItemRepository.deleteById(todoitem.getId());
        }
        return todoitem;
    }
}

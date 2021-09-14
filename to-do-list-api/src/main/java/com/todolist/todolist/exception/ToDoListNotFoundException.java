package com.todolist.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ToDoListNotFoundException extends RuntimeException{
    public ToDoListNotFoundException(String message){
        super(message);
    }
}

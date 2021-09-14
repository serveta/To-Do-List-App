package com.todolist.todolist.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ToDoListDto {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50, message = "Name should have at least 2, max 50 characters.")
    private String name;

    @NotNull
    @NotEmpty
    @DateTimeFormat
    private Date expirationDate;

   // private List<ToDoItemDto> toDoItemDtos = new ArrayList<>();

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Date getExpirationDate(){
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate){
        this.expirationDate = expirationDate;
    }
/*
    public List<ToDoItemDto> getToDoItemDtos() {
        return toDoItemDtos;
    }
    public void setToDoItemDtos(List<ToDoItemDto> toDoItemDtos) {
        this.toDoItemDtos = toDoItemDtos;
    }
*/
}

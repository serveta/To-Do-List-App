package com.todolist.todolist.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ToDoItemDto {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50, message = "Name should have at least 2, max 50 characters.")
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 500, message = "Name should have at least 2, max 500 characters.")
    private String description;

    @NotNull
    @NotEmpty
    @DateTimeFormat
    private Date startingDate;

    @NotNull
    @NotEmpty
    @DateTimeFormat
    private Date deadline;

    @NotNull
    @NotEmpty
    private String status;


    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public Date getStartingDate(){
        return startingDate;
    }
    public void setStartingDate(Date startingDate){
        this.startingDate = startingDate;
    }

    public Date getDeadline(){
        return deadline;
    }
    public void setDeadline(Date deadline){
        this.deadline = deadline;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

}

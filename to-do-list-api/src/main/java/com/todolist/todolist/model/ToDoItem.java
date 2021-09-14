package com.todolist.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ToDoItem")
public class ToDoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description", length = 500)
    private String description;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="startingDate")
    private Date startingDate;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="deadline")
    private Date deadline;

    @Column(name="status", length = 1)
    private String status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "toDoListId")
    private ToDoList toDoList;

    public ToDoItem(){

    }
    public ToDoItem(String name, String description, Date startingDate,
                    Date deadline, String status, ToDoList toDoList){
        this.name = name;
        this.description = description;
        this.startingDate = startingDate;
        this.deadline = deadline;
        this.status = status;
        this.toDoList = toDoList;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

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

    public ToDoList getToDoList() {
        return toDoList;
    }
    public void setToDoList(ToDoList toDoList) {
        this.toDoList = toDoList;
    }
}

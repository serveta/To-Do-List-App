package com.todolist.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ToDoList")
public class ToDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="name")
    private String name;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name="expirationDate")
    private Date expirationDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "toDoList", cascade = CascadeType.ALL)
    private List<ToDoItem> toDoItems;

    public ToDoList(){

    }
    public ToDoList(String name, Date expirationDate, User user){
        this.name = name;
        this.expirationDate = expirationDate;
        this.user = user;
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

    public Date getExpirationDate(){
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate){
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public List<ToDoItem> getToDoItems() {
        return toDoItems;
    }
    public void setToDoItems(List<ToDoItem> toDoItems) {
        this.toDoItems = toDoItems;
    }
}

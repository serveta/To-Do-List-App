package com.todolist.todolist.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ToDoList> toDoLists;

    public User(){

    }
    public User(String name, String surname, String email, String password){
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
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

    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public List<ToDoList> getToDoLists() {
        return toDoLists;
    }
    public void setToDoLists(List<ToDoList> toDoLists) {
        this.toDoLists = toDoLists;
    }
}

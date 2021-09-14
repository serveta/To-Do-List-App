package com.todolist.todolist.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50, message = "Name should have at least 2, max 50 characters.")
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50, message = "Surname should have at least 2, max 50 characters.")
    private String surname;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 25, message = "Password should have at least 8, max 25 characters.")
    private String password;


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

}

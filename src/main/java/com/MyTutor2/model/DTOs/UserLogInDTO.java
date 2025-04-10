package com.MyTutor2.model.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLogInDTO {


    @NotBlank
    @Size(min=2, max=40)
    private String username;

    @NotBlank
    @Size(min=2, max=40)
    private String password;

    public UserLogInDTO() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

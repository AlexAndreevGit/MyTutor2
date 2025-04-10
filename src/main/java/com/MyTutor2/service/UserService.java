package com.MyTutor2.service;


import com.MyTutor2.exceptions.TutorialNotFoundException;
import com.MyTutor2.model.DTOs.UserRegisterDTO;
import com.MyTutor2.model.entity.User;

public interface UserService {
    void registerUser(UserRegisterDTO userRegisterDTO);

    void deleteUser(User logedInUser) throws TutorialNotFoundException;
}

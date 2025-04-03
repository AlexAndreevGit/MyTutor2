package com.MyTutor2.Exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String muserName) {
        super("User not found with username: " + muserName);
    }
}

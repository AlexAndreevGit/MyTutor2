package com.MyTutor2.exceptions;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String muserName) {
        super("User not found with username: " + muserName);
    }
}

package com.MyTutor2.Exceptions;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String muserName) {
        super("User not found with username: " + muserName);
    }
}

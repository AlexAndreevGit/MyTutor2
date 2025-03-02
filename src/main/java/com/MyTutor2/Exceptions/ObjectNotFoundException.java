package com.MyTutor2.Exceptions;


//SpringSecurity_8
public class ObjectNotFoundException extends RuntimeException{
    //It extends RuntimeException, which makes it an unchecked exception -> it does not require mandatory handling using try-catch or throws
    //The creating a custom exception I improve the code clarity because I provide meaningfully name for the exception instead of teh generic RuntimeException

    public ObjectNotFoundException(String message) {
        super(message);
    }

}

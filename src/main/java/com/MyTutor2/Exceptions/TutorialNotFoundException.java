package com.MyTutor2.Exceptions;

public class TutorialNotFoundException extends RuntimeException {

    public TutorialNotFoundException(Long id) {
        super("Tutorial not found with id: " + id);
    }
}

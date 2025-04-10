package com.MyTutor2.exceptions;

public class TutorialNotFoundException extends Exception {

    public TutorialNotFoundException(Long id) {
        super("Tutorial not found with id: " + id);
    }
}

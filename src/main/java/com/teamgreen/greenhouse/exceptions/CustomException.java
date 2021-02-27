package com.teamgreen.greenhouse.exceptions;

public class CustomException extends Exception {

    public CustomException(String s) {
        super(s);
    }

    public CustomException(String s, Exception e) {
        super(s, e);
    }
}

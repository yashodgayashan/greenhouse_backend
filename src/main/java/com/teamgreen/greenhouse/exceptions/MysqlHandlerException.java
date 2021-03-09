package com.teamgreen.greenhouse.exceptions;

public class MysqlHandlerException extends Exception {

    public MysqlHandlerException(String s) {
        super(s);
    }

    public MysqlHandlerException(String s, Exception e) {
        super(s, e);
    }
}

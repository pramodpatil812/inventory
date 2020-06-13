package com.sayurbox.exceptions;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String msg) {
        super(msg);
    }
}

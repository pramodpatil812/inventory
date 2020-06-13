package com.sayurbox.exceptions;

public class NotEnoughQuantityException extends RuntimeException {
    public NotEnoughQuantityException(String msg) {
        super(msg);
    }
}

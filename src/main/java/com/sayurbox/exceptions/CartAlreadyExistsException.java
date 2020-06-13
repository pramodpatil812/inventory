package com.sayurbox.exceptions;

public class CartAlreadyExistsException extends RuntimeException {
    public CartAlreadyExistsException(String msg) {
        super(msg);
    }
}

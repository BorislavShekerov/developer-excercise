package com.exercise.cloudruid.utils.exceptions;

public class ItemExistsException extends RuntimeException{
    public ItemExistsException(String message) {
        super(message);
    }
}

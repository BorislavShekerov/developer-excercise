package com.exercise.cloudruid.utils.exceptions;

public class GrocerieNotFoundException extends RuntimeException{
    public GrocerieNotFoundException(String msg) {
        super(msg);
    }
}

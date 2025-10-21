package com.yostoya.shoptill.exception.alreadyexist;

public class ItemAlreadyExistException extends AlreadyExistException {
    public ItemAlreadyExistException(String message) {
        super(message);
    }

    public ItemAlreadyExistException(String field, String value) {
        super("Item", field, value);
    }
}

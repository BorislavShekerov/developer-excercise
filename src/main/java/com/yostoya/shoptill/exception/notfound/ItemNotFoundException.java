package com.yostoya.shoptill.exception.notfound;

public class ItemNotFoundException extends NotFoundException {
    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(String field, String value) {
        super("Item", field, value);
    }
}

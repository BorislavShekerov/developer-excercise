package com.yostoya.shoptill.exception.notfound;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String field, String value) {
        super("User", field, value);
    }
}

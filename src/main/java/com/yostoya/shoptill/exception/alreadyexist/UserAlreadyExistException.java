package com.yostoya.shoptill.exception.alreadyexist;

public class UserAlreadyExistException extends AlreadyExistException {
    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(String field, String value) {
        super("User", field, value);
    }
}

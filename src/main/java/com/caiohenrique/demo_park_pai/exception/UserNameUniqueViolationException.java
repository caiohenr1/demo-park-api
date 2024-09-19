package com.caiohenrique.demo_park_pai.exception;

public class UserNameUniqueViolationException extends RuntimeException {
    public UserNameUniqueViolationException(String message) {
        super(message);
    }
}

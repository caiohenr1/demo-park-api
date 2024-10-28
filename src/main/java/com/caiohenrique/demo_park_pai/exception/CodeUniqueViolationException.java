package com.caiohenrique.demo_park_pai.exception;

public class CodeUniqueViolationException extends RuntimeException {
    public CodeUniqueViolationException(String message) {
        super(message);
    }
}

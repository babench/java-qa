package ru.otus.zaikin.otus.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super("Login exception " + message);
    }
}

package ru.otus.zaikin.drive2.exception;

public class RecordNotExistsException extends RuntimeException {
    public RecordNotExistsException() {
        super();
    }

    public RecordNotExistsException(String message) {
        super("Record for found for {MyTable:Id} => " + message);
    }
}

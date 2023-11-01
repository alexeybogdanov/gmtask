package com.example.gmtask.error;

public class CsvItemNotFoundException extends RuntimeException {

    public CsvItemNotFoundException(String message) {
        super(message);
    }
}

package com.csvconverter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidFileTypeException extends RuntimeException {

    public InvalidFileTypeException() {
        super("Invalid file type");
    }
}

package com.oocl.jpamysql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNullException extends RuntimeException {
    public ResourceNullException(String message) {
        super(message);
    }
}

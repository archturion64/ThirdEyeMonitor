package com.github.archturion64.thirdeye.handlers;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class StorageExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public String handleException(Exception ex) {
        return ex.getMessage();
    }
}

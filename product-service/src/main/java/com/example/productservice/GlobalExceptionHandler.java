package com.example.productservice;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var message = ex.getMessage() != null ? ex.getMessage() : "Unexpected error";

        var annotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (annotation != null) {
            status = annotation.code();
            if (!annotation.reason().isEmpty()) {
                message = annotation.reason();
            }
        }

        var error = new ErrorResponse(message);
        return new ResponseEntity<>(error, status);
    }

    public record ErrorResponse(String message){}

}

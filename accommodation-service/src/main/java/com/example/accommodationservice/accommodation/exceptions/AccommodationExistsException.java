package com.example.accommodationservice.accommodation.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Accommodation with this name already exists")
public class AccommodationExistsException extends RuntimeException {
    public AccommodationExistsException(String message) {
        super(message);
    }
}

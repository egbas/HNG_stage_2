package com.egbas.HNG_stage_two.exceptions;

import com.egbas.HNG_stage_two.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(final UserNotFoundException ex){
        ErrorDetails errorRequest = ErrorDetails.builder()
                .message(ex.getMessage())
                .debugMessage("Username Does Not Exist")
                .errorTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorRequest, HttpStatus.NOT_FOUND);
    }
}

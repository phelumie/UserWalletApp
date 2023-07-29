package com.ajisegiri.usermanagement.exceptions.handler;


import com.ajisegiri.usermanagement.exceptions.FirebaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(FirebaseException.class)
    public ResponseEntity<Object> storeHarmonyClientExceptions(FirebaseException firebaseException){
        HttpStatus status=HttpStatus.BAD_REQUEST;
        ApiExceptionMessage exception=new ApiExceptionMessage(
                firebaseException.getMessage(),
                HttpStatus.valueOf(firebaseException.getStatusCode()),
                ZonedDateTime.now(ZoneId.of("UTC+1")));
        return new ResponseEntity<>(exception,status);
    }




}

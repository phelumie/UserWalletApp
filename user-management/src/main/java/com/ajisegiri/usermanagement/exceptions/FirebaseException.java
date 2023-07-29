package com.ajisegiri.usermanagement.exceptions;

import lombok.Getter;

@Getter
public class FirebaseException extends RuntimeException {
    private final int statusCode;

    public FirebaseException(String message,int statusCode) {
        super(message);
        this.statusCode=statusCode;
    }
}

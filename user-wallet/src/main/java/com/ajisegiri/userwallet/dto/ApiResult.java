package com.ajisegiri.userwallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class ApiResult<T> {

    private boolean success;
    private String message;
    private T content;
}
package com.ajisegiri.userwallet.dto;

import com.ajisegiri.userwallet.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWallet implements Serializable {
    private String userId;
    private Currency currency;
}
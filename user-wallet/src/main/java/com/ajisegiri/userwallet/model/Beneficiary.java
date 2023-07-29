package com.ajisegiri.userwallet.model;

import lombok.Data;

@Data
public class Beneficiary {
    private String userId;
    private String walletId;
    private String beneficiaryName;
}

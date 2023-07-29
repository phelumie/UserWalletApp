package com.ajisegiri.usermanagement.dto;

import com.ajisegiri.usermanagement.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletPayload implements Serializable {
    private Long userId;
    private Currency currency;

}

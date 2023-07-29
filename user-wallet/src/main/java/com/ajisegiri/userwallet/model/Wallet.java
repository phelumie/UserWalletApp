package com.ajisegiri.userwallet.model;

import com.ajisegiri.userwallet.enums.Currency;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = {"transactions","scheduledDeposits"})
@NoArgsConstructor
public class Wallet {
    @Id
    private UUID walletId;
    private String userId;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToMany(mappedBy = "sourceWallet",fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "wallet",fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ScheduledDeposit> scheduledDeposits;





}
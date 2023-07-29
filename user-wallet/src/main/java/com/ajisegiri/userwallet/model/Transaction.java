package com.ajisegiri.userwallet.model;

import com.ajisegiri.userwallet.enums.TransactionStatus;
import com.ajisegiri.userwallet.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
//@ToString(exclude = {"recipientWallet","scheduledDeposit","sourceWallet"})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @ManyToOne
    @JoinColumn(name = "recipient_wallet_id")
    @JsonBackReference
    private Wallet recipientWallet;
    @ManyToOne
    @JoinColumn(name = "source_wallet_id")
    @JsonBackReference
    private Wallet sourceWallet;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "scheduled_deposit_id")
    @JsonBackReference
    private ScheduledDeposit scheduledDeposit;
    private LocalDateTime timestamp;

}
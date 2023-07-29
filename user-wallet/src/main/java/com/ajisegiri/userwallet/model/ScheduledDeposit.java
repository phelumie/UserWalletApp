package com.ajisegiri.userwallet.model;

import com.ajisegiri.userwallet.enums.Frequency;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
//@ToString(exclude = {"wallet"})
public class ScheduledDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    @Column(nullable = false)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency;
    @Column(nullable = false)
    private String beneficiaries;
    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(nullable = false)
    private LocalDate nextScheduledDate;
    @Column(nullable = false)
    private boolean isActive=true;
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "wallet_id")
    @JsonBackReference
    private Wallet wallet;
    private LocalDateTime createdDate;

    public List<Beneficiary> getBeneficiariesASList()  {
            ObjectMapper mapper = new ObjectMapper();
            var typeRef = new TypeReference<List<Beneficiary>>(){};
        try {
            return mapper.readValue(beneficiaries,typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}

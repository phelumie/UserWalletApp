package com.ajisegiri.userwallet.dto;

import com.ajisegiri.userwallet.enums.Frequency;
import com.ajisegiri.userwallet.model.Beneficiary;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateScheduledDeposit {
    @NotBlank
    private String walletId;
    @NotBlank
    private BigDecimal amount;
    @NotNull
    private Frequency frequency;
    @NotEmpty
    private List<Beneficiary> beneficiaries;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive=true;

}

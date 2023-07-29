package com.ajisegiri.userwallet.service.impl;

import com.ajisegiri.userwallet.dto.CreateScheduledDeposit;
import com.ajisegiri.userwallet.enums.Frequency;
import com.ajisegiri.userwallet.model.Beneficiary;
import com.ajisegiri.userwallet.model.ScheduledDeposit;
import com.ajisegiri.userwallet.repo.ScheduledDepositRepository;
import com.ajisegiri.userwallet.repo.WalletRepository;
import com.ajisegiri.userwallet.service.ScheduleDepositService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleDepositServiceImpl implements ScheduleDepositService {
    private final ScheduledDepositRepository scheduledDepositRepository;
    private final ObjectMapper mapper;
    private final WalletRepository walletRepository;
    @SneakyThrows
    @Override
    public ScheduledDeposit create(CreateScheduledDeposit scheduledDeposit) {
        log.debug("creating Schedule deposit for {}  ",scheduledDeposit.getWalletId());
        LocalTime currentTime = LocalTime.now();

        var deposit=new ScheduledDeposit();
        deposit.setWallet(walletRepository.getReferenceById(UUID.fromString(scheduledDeposit.getWalletId())));
        deposit.setAmount(scheduledDeposit.getAmount());
        deposit.setFrequency(scheduledDeposit.getFrequency());
        deposit.setStartDate(scheduledDeposit.getStartDate());
        deposit.setEndDate(scheduledDeposit.getEndDate());
        deposit.setCreatedDate(LocalDateTime.now());
        deposit.setBeneficiaries(mapper.writeValueAsString(scheduledDeposit.getBeneficiaries()));

        if (isTodayTheStartDate(deposit.getStartDate()) && currentTime.isAfter(LocalTime.of(11, 59))) {
            // If today is the start date and the current time is after 11:59 AM
            // Set the next scheduled date to tomorrow date since the scheduler runs at 12 PM daily
            deposit.setNextScheduledDate(LocalDate.now().plusDays(1));
        } else {
            // If today is not the start date or the current time is before or equal to 11:59 AM
            // Set the next scheduled date to the start date itself
            deposit.setNextScheduledDate(deposit.getStartDate());
        }

        var save=scheduledDepositRepository.save(deposit);
        log.info("successfully created schedule deposit for with wallet id {} ",scheduledDeposit.getWalletId());
        return save;
    }

    @Override
    public List<ScheduledDeposit> getAllScheduleDeposits() {
        return scheduledDepositRepository.findAll();
    }

    private boolean isTodayTheStartDate(LocalDate startDate) {
        return startDate.equals(LocalDate.now());
    }



}

package com.ajisegiri.userwallet.service;

import com.ajisegiri.userwallet.dto.CreateScheduledDeposit;
import com.ajisegiri.userwallet.model.ScheduledDeposit;

import java.util.List;

public interface ScheduleDepositService {
    ScheduledDeposit create(CreateScheduledDeposit scheduledDeposit);

    List<ScheduledDeposit> getAllScheduleDeposits();
}

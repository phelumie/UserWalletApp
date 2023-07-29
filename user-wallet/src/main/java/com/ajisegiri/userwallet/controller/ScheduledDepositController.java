package com.ajisegiri.userwallet.controller;

import com.ajisegiri.userwallet.dto.ApiResult;
import com.ajisegiri.userwallet.dto.CreateScheduledDeposit;
import com.ajisegiri.userwallet.model.ScheduledDeposit;
import com.ajisegiri.userwallet.service.ScheduleDepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/deposit/schedule")
public class ScheduledDepositController {
    private final ScheduleDepositService scheduleDepositService;


    @PostMapping
    public ResponseEntity<ApiResult<ScheduledDeposit>> createScheduleDeposit(@RequestBody CreateScheduledDeposit scheduledDeposit){
        var result=scheduleDepositService.create(scheduledDeposit);

        var response=new ApiResult(true,"Successfully created schedule Deposit",result);

        return  new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<ScheduledDeposit>>> getAllScheduleDeposits(){
        var result=scheduleDepositService.getAllScheduleDeposits();

        var response=new ApiResult(true,"Successfully retrieved all schedule Deposits",result);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

}

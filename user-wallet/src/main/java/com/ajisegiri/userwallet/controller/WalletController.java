package com.ajisegiri.userwallet.controller;

import com.ajisegiri.userwallet.dto.ApiResult;
import com.ajisegiri.userwallet.dto.CreateScheduledDeposit;
import com.ajisegiri.userwallet.model.ScheduledDeposit;
import com.ajisegiri.userwallet.model.Wallet;
import com.ajisegiri.userwallet.service.ScheduleDepositService;
import com.ajisegiri.userwallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/wallet")
@RestController
public class WalletController {
    private final WalletService walletService;


    @GetMapping
    public ResponseEntity<ApiResult<List<Wallet>>> getAllScheduleDeposits(){
        var result=walletService.getAllWallets();

        var response=new ApiResult(true,"Successfully retrieved all wallet accounts",result);

        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

}

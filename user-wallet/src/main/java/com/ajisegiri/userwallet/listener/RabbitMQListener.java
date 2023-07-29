package com.ajisegiri.userwallet.listener;

import com.ajisegiri.userwallet.dto.CreateWallet;
import com.ajisegiri.userwallet.service.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {
    private final WalletService walletService;

    @RabbitListener(queues = "walletQueue")
    public void receiveNotification(CreateWallet payload) throws JsonProcessingException {
        walletService.createWallet(payload);
    }
}

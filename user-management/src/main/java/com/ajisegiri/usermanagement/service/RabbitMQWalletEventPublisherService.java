package com.ajisegiri.usermanagement.service;

import com.ajisegiri.usermanagement.dto.WalletPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQWalletEventPublisherService implements EventPublisher<WalletPayload> {

    private final RabbitTemplate amqpTemplate;
    private final DirectExchange directExchange;

    @Override
    public void publish(WalletPayload payload,String routingKey) {
        // Logic for sending email notification

        amqpTemplate.convertAndSend(directExchange.getName(), routingKey, payload);
        log.info("Sending wallet event to {} : {}" ,payload.getUserId(), payload);


    }


}

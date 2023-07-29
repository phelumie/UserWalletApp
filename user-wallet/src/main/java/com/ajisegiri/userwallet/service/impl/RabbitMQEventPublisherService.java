package com.ajisegiri.userwallet.service.impl;

import com.ajisegiri.userwallet.dto.NotificationPayload;
import com.ajisegiri.userwallet.service.EventPublisher;
import com.fasterxml.jackson.core.Base64Variant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.ajisegiri.userwallet.config.RabbitMQDirectConfig.NOTIFICATION_QUEUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQEventPublisherService implements EventPublisher<NotificationPayload> {

    private final RabbitTemplate amqpTemplate;
    private final DirectExchange directExchange;

    @Override
    public void publish(NotificationPayload payload,String routingKey) {
        // Logic for sending email notification
        log.info("Sending {} notification to {} " ,payload.getNotificationType(),payload.getUserId());

        amqpTemplate.convertAndSend(directExchange.getName(), routingKey, payload);


    }


}

package com.ajisegiri.notificationsystem.listener;

import com.ajisegiri.notificationsystem.dto.NotificationPayload;
import com.ajisegiri.notificationsystem.enums.NotificationType;
import com.ajisegiri.notificationsystem.service.NotificationStrategyFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    @RabbitListener(queues = "notificationQueue")
    public void receiveNotification(NotificationPayload notificationPayload) {

        NotificationType notificationType=notificationPayload.getNotificationType();
        var service=NotificationStrategyFactory.findEventServiceCall(notificationType);

        if (Objects.nonNull(service)){
            service.process(notificationPayload);
        }
        // persist event
    }

}

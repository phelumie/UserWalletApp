package com.ajisegiri.notificationsystem.service.strategy;

import com.ajisegiri.notificationsystem.dto.NotificationPayload;
import com.ajisegiri.notificationsystem.enums.NotificationType;
import com.ajisegiri.notificationsystem.service.NotificationEventStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class EmailNotificationStrategy  implements NotificationEventStrategy {
    @Override
    public void process(NotificationPayload notificationPayload) {
        notificationPayload.setMessage(notificationPayload.getMessage().formatted("sunday"));
        log.info("processing email NotificationPayload");

    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.EMAIL;
    }

//    @Override
//    public ShopifyWebhookTopic getTopic() {
//        return ShopifyWebhookTopic.PRODUCTS_DELETE;
//    }
}

package com.ajisegiri.notificationsystem.service.strategy;

import com.ajisegiri.notificationsystem.dto.NotificationPayload;
import com.ajisegiri.notificationsystem.enums.NotificationType;
import com.ajisegiri.notificationsystem.service.NotificationEventStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class MobileNotificationStrategy implements NotificationEventStrategy {


    @Override
    public void process(NotificationPayload notificationPayload) {
        log.info("processing mobile NotificationPayload");
    }


    @Override
    public NotificationType getNotificationType() {
        return NotificationType.MOBILE;
    }

}

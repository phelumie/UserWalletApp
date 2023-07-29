package com.ajisegiri.notificationsystem.service;


import com.ajisegiri.notificationsystem.dto.NotificationPayload;
import com.ajisegiri.notificationsystem.enums.NotificationType;

import java.util.List;

public interface NotificationEventStrategy  {

    void process(NotificationPayload notificationPayload);
    NotificationType getNotificationType();

}

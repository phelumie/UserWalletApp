package com.ajisegiri.notificationsystem.service;

import com.ajisegiri.notificationsystem.enums.NotificationType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
    An  immutable class

 */
@Component

public final class NotificationStrategyFactory {
    private static Map<NotificationType, NotificationEventStrategy> map;

    public NotificationStrategyFactory (Set<NotificationEventStrategy> webhookApiStrategySet) {
        createStrategy(webhookApiStrategySet);
    }

    public static NotificationEventStrategy findEventServiceCall(NotificationType notificationType){
        return map.get(notificationType);
    }

    private void createStrategy(Set<NotificationEventStrategy> eventStrategies) {
        map = eventStrategies.stream()
                .collect(Collectors.toUnmodifiableMap(NotificationEventStrategy::getNotificationType, Function.identity(),
                        (oldValue, newValue) -> oldValue));
    }

}

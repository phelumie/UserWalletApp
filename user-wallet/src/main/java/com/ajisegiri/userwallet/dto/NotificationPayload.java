package com.ajisegiri.userwallet.dto;

import com.ajisegiri.userwallet.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPayload implements Serializable {
    private String userId;
    private String message;
    private String subject;
    private NotificationType notificationType;
}


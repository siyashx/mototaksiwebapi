package com.codesupreme.mototaksiwebapi.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotificationDto {

    private Long id;

    private String chatId;
    private String userId;
    private String message;
    private String isRead;
    private String type;
    private String isDeleted;
    private String createdAt;

}

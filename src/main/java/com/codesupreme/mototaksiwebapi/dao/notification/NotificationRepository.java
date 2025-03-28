package com.codesupreme.mototaksiwebapi.dao.notification;

import com.codesupreme.mototaksiwebapi.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

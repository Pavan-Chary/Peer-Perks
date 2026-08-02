package in.pavan.peer_perk_ledger.service;

import in.pavan.peer_perk_ledger.model.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    void sendNotification(UUID userId, String message);
    List<Notification> getMyNotifications(UUID userId);
    List<Notification> viewNotifications(UUID userId);
}

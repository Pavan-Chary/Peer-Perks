package in.pavan.peer_perk_ledger.service;

import in.pavan.peer_perk_ledger.model.Notification;
import in.pavan.peer_perk_ledger.repository.NotificationRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepo notificationRepo;

    @Override
    @Transactional
    public void sendNotification(UUID userId, String message) {
        Notification notification = Notification.builder()
                .receiverId(userId)
                .body(message)
                .isRead(false)
                .build();
        notificationRepo.save(notification);
    }

    @Override
    public List<Notification> getMyNotifications(UUID userId) {
        return notificationRepo.findByReceiverIdOrderByCreatedAtDesc(userId);
    }

    @Override
    @Transactional
    public List<Notification> viewNotifications(UUID userId){
        notificationRepo.markAllNotificationsAsRead(userId);
        return notificationRepo.findByReceiverIdOrderByCreatedAtDesc(userId);
    }

}

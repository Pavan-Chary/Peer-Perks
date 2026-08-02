package in.pavan.peer_perk_ledger.repository;

import in.pavan.peer_perk_ledger.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverIdOrderByCreatedAtDesc(UUID userId);
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.receiverId = :user_id AND n.isRead = false")
    void markAllNotificationsAsRead(@Param("user_id") UUID userId);
}

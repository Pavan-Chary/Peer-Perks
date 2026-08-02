package in.pavan.peer_perk_ledger.controller;

import in.pavan.peer_perk_ledger.model.Notification;
import in.pavan.peer_perk_ledger.model.User;
import in.pavan.peer_perk_ledger.service.NotificationService;
import in.pavan.peer_perk_ledger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Notification>> getMyNotifications(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        User user = userService.getUserByEmail(email);

        return ResponseEntity.ok(notificationService.getMyNotifications(user.getId()));
    }

    @PostMapping("/read")
    public ResponseEntity<List<Notification>> markAllAsReadAndFetch(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(notificationService.viewNotifications(user.getId()));
    }

}

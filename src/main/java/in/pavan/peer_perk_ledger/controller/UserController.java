package in.pavan.peer_perk_ledger.controller;

import in.pavan.peer_perk_ledger.dto.user_dto.UserResponse;
import in.pavan.peer_perk_ledger.dto.user_dto.UserSummaryResponse;
import in.pavan.peer_perk_ledger.dto.user_dto.UserUpdateRequest;
import in.pavan.peer_perk_ledger.enums.UserRole;
import in.pavan.peer_perk_ledger.exception.UnauthorizedAccessException;
import in.pavan.peer_perk_ledger.model.User;
import in.pavan.peer_perk_ledger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {

        String email = principal.getAttribute("email");

        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserResponse.fromEntity(user));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSummaryResponse> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserSummaryResponse.fromEntity(user));
    }

    @GetMapping("/search")
    public ResponseEntity<UserSummaryResponse> getUserByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserSummaryResponse.fromEntity(user));
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkUserExists(@RequestParam String email) {
        boolean exists = userService.checkUserWithEmail(email);
        return ResponseEntity.ok(exists);
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateUserProfile(
            @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal OAuth2User principal
    ) {
        User currentUser = userService.getUserByEmail(principal.getAttribute("email"));

        User updatedUser = userService.updateUser(currentUser.getId(), request.name());
        return ResponseEntity.ok(UserResponse.fromEntity(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deactivateUser(
            @PathVariable UUID id,
            @AuthenticationPrincipal OAuth2User principal
        ) {
        User currentUser = userService.getUserByEmail(principal.getAttribute("email"));
        if (id.equals(currentUser.getId()) || currentUser.getRole()== UserRole.ROLE_ADMIN) {
            throw new UnauthorizedAccessException("You are not authorized to do this operation");
        }
        User deactivatedUser = userService.deactivateUser(id);
        return ResponseEntity.ok(UserResponse.fromEntity(deactivatedUser));
    }
}
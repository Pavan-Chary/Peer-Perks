package in.pavan.peer_perk_ledger.dto.user_dto;

import in.pavan.peer_perk_ledger.enums.AccountStatus;
import in.pavan.peer_perk_ledger.enums.UserRole;
import in.pavan.peer_perk_ledger.model.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String name,
        UserRole role,
        Integer allowanceBalance,
        Integer redeemableBalance,
        AccountStatus status
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getAllowanceBalance(),
                user.getRedeemableBalance(),
                user.getStatus()
        );
    }
}

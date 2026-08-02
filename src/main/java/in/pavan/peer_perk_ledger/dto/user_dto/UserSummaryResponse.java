package in.pavan.peer_perk_ledger.dto.user_dto;

import in.pavan.peer_perk_ledger.model.User;

import java.util.UUID;

public record UserSummaryResponse(
        UUID id,
        String name,
        String email
) {
    public static UserSummaryResponse fromEntity(User user){
        return new UserSummaryResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}

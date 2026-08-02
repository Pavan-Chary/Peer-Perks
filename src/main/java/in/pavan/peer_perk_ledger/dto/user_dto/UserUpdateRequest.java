package in.pavan.peer_perk_ledger.dto.user_dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @NotBlank(message = "Name cannot be blank")
        String name
) { }

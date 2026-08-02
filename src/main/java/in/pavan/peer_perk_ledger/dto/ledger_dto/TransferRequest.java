package in.pavan.peer_perk_ledger.dto.ledger_dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TransferRequest(
        @NotNull(message = "Receiver ID is required")
        UUID receiverId,

        @Min(value = 1, message = "Points must be at least 1")
        int points,

        @NotBlank(message = "Message should not be blank")
        String message
) {
}

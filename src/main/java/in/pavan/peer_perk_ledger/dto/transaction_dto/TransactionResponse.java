package in.pavan.peer_perk_ledger.dto.transaction_dto;

import in.pavan.peer_perk_ledger.model.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID senderId,
        UUID receiverId,
        Integer points,
        String message,
        String type,
        LocalDateTime timestamp
) {
    public static TransactionResponse fromEntity(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getSenderId(),
                transaction.getReceiverId(),
                transaction.getPoints(),
                transaction.getMessage(),
                transaction.getType() != null ? transaction.getType().name() : null,
                transaction.getTimestamp()
        );
    }
}

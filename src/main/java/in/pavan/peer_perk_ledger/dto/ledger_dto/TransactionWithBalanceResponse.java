package in.pavan.peer_perk_ledger.dto.ledger_dto;

import in.pavan.peer_perk_ledger.model.Transaction;
import in.pavan.peer_perk_ledger.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionWithBalanceResponse(
        UUID id,
        String name,
        UUID senderId,
        UUID receiverId,
        Integer points,
        Integer allowanceBalance,
        Integer redeemableBalance,
        String message,
        String type,
        LocalDateTime timestamp
) {
    public static TransactionWithBalanceResponse fromEntity(Transaction transaction, User user) {
        return new TransactionWithBalanceResponse(
                transaction.getId(),
                user.getName(),
                transaction.getSenderId(),
                transaction.getReceiverId(),
                transaction.getPoints(),
                user.getAllowanceBalance(),
                user.getRedeemableBalance(),
                transaction.getMessage(),
                transaction.getType() != null ? transaction.getType().name() : null,
                transaction.getTimestamp()
        );
    }
}

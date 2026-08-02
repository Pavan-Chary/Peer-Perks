package in.pavan.peer_perk_ledger.service;

import in.pavan.peer_perk_ledger.model.Transaction;

import java.util.Map;
import java.util.UUID;

public interface LedgerService {
    Transaction transferPoints(UUID senderId, UUID receiverId, int points, String message);
    Transaction redeemPoints(UUID userId, UUID adminId, Map<Long, Integer>orderItems);
    Transaction monthlyResetPoints(UUID userId);
}

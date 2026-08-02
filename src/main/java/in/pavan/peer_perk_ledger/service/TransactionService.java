package in.pavan.peer_perk_ledger.service;

import in.pavan.peer_perk_ledger.dto.transaction_dto.TransactionFilter;
import in.pavan.peer_perk_ledger.model.Transaction;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TransactionService {
    Page<Transaction> getAllTransactionOfUser(UUID userId, int pageNum, int pageSize, TransactionFilter filter);
}

package in.pavan.peer_perk_ledger.repository;

import in.pavan.peer_perk_ledger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TransactionRepo extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {

}

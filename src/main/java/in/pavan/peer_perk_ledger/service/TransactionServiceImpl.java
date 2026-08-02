package in.pavan.peer_perk_ledger.service;

import in.pavan.peer_perk_ledger.dto.transaction_dto.TransactionFilter;
import in.pavan.peer_perk_ledger.model.Transaction;
import in.pavan.peer_perk_ledger.repository.TransactionRepo;
import in.pavan.peer_perk_ledger.repository.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepo transactionRepo;

    @Override
    public Page<Transaction> getAllTransactionOfUser(UUID userId, int pageNum, int pageSize, TransactionFilter filter) {

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("timestamp").descending());
        Specification<Transaction> specificationFilter = TransactionSpecification.getByUserIdWithFilter(userId, filter);

        return transactionRepo.findAll(specificationFilter, pageable);

    }
}

package in.pavan.peer_perk_ledger.job;

import in.pavan.peer_perk_ledger.constants.TransactionConstants;
import in.pavan.peer_perk_ledger.enums.TransactionInitiated;
import in.pavan.peer_perk_ledger.enums.TransactionType;
import in.pavan.peer_perk_ledger.model.Transaction;
import in.pavan.peer_perk_ledger.repository.TransactionRepo;
import in.pavan.peer_perk_ledger.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class MonthlyResetJob {
    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;

    @Transactional
    @Retryable(
            value = {DataAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    @Scheduled(cron = "0 0 0 1 * ?")
    public void executeMonthlyReset() {
        System.out.println("Starting automated monthly allowance reset...");

        userRepo.resetAllAllowanceBalances(TransactionConstants.ADD_POINTS);

        int pageNumber = 0;
        int batchSize = 500;
        Slice<UUID> userPage;
        int totalProcessed = 0;

        do {
            Pageable pageable = PageRequest.of(pageNumber, batchSize);

            userPage = userRepo.findAllActiveUserIds(pageable);

            List<Transaction> resetTransactions = userPage.getContent().stream().map(userId ->{
                Transaction transaction = new Transaction();
                    transaction.setReceiverId(userId);
                    transaction.setPoints(TransactionConstants.ADD_POINTS);
                    transaction.setType(TransactionType.WALLET_RESET);
                    transaction.setInitiatedBy(TransactionInitiated.SYSTEM);
                    transaction.setMessage("Automated Monthly Allowance Reset");
                    return transaction;
                }
            ).toList();

            transactionRepo.saveAll(resetTransactions);

            totalProcessed += resetTransactions.size();
            pageNumber++;

        } while (userPage.hasNext());

        System.out.printf("Monthly allowance reset complete! %d users now have %d points.\n",
                totalProcessed, TransactionConstants.ADD_POINTS);
    }

    @Recover
    public void recoverFromFailure(DataAccessException e) {
        System.out.println("CRITICAL ALARM: Monthly reset failed 3 times in a row! Database might be down. Error: "+e.getMessage());
    }
}


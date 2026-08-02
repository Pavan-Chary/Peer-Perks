package in.pavan.peer_perk_ledger.controller;

import in.pavan.peer_perk_ledger.dto.ledger_dto.RedemptionRequest;
import in.pavan.peer_perk_ledger.dto.ledger_dto.TransactionWithBalanceResponse;
import in.pavan.peer_perk_ledger.dto.ledger_dto.TransferRequest;
import in.pavan.peer_perk_ledger.model.Transaction;
import in.pavan.peer_perk_ledger.model.User;
import in.pavan.peer_perk_ledger.service.LedgerService;
import in.pavan.peer_perk_ledger.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ledger")
@RequiredArgsConstructor
public class LedgerController {

    private final LedgerService ledgerService;
    private final UserService userService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionWithBalanceResponse> transferPoints(
            @Valid @RequestBody TransferRequest request,
            @AuthenticationPrincipal OAuth2User principal
            ) {
        String email = principal.getAttribute("email");
        User currentUser = userService.getUserByEmail(email);
        Transaction transaction = ledgerService.transferPoints(
                currentUser.getId(),
                request.receiverId(),
                request.points(),
                request.message()
        );

        User user = userService.getUserById(currentUser.getId());

        return ResponseEntity.ok(TransactionWithBalanceResponse.fromEntity(transaction, user));
    }

    @PostMapping("/redeem")
    public ResponseEntity<TransactionWithBalanceResponse> redeemPoints(
            @Valid @RequestBody RedemptionRequest request,
            @AuthenticationPrincipal OAuth2User principal
    ) {

        String email = principal.getAttribute("email");
        User currentUser = userService.getUserByEmail(email);

        Transaction transaction = ledgerService.redeemPoints(
                request.employeeId(),
                currentUser.getId(),
                request.orderItems()
        );
        User user = userService.getUserById(request.employeeId());

        return ResponseEntity.ok(TransactionWithBalanceResponse.fromEntity(transaction, user));
    }


    @PostMapping("/monthly-reset/{userId}")
    public ResponseEntity<TransactionWithBalanceResponse> triggerMonthlyReset(@PathVariable UUID userId) {
        Transaction transaction = ledgerService.monthlyResetPoints(userId);
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(TransactionWithBalanceResponse.fromEntity(transaction, user));
    }
}

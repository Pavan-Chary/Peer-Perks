package in.pavan.peer_perk_ledger.controller;

import in.pavan.peer_perk_ledger.dto.transaction_dto.TransactionFilter;
import in.pavan.peer_perk_ledger.dto.transaction_dto.TransactionResponse;
import in.pavan.peer_perk_ledger.model.User;
import in.pavan.peer_perk_ledger.service.TransactionService;
import in.pavan.peer_perk_ledger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<TransactionResponse>> getMyTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Integer minPoints,
            @RequestParam(required = false) Integer maxPoints,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @AuthenticationPrincipal OAuth2User principal
    ) {

        String email = principal.getAttribute("email");
        User currentUser = userService.getUserByEmail(email);

        TransactionFilter filter = new TransactionFilter(minPoints, maxPoints, startDate, endDate);

        var transactionPage = transactionService.getAllTransactionOfUser(
                currentUser.getId(), page, size, filter
        );
        Page<TransactionResponse> responsePage = transactionPage.map(TransactionResponse::fromEntity);

        return ResponseEntity.ok(responsePage);
    }
}

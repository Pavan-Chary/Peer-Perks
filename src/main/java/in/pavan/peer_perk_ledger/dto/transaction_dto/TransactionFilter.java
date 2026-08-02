package in.pavan.peer_perk_ledger.dto.transaction_dto;

import java.time.LocalDateTime;

public record TransactionFilter (
    Integer minPoints,
    Integer maxPoints,
    LocalDateTime startDate,
    LocalDateTime endDate
){}

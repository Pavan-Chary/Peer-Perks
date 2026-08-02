package in.pavan.peer_perk_ledger.dto.ledger_dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record RedemptionRequest(
        @NotNull(message = "Employee ID is required")
        UUID employeeId,

        @NotNull(message = "Order Items should not be null")
        @NotEmpty(message = "Order items cannot be empty")
        Map<Long, Integer> orderItems
) {
}

package in.pavan.peer_perk_ledger.model;

import in.pavan.peer_perk_ledger.enums.TransactionInitiated;
import in.pavan.peer_perk_ledger.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID senderId;
    private UUID receiverId;

    private Integer points;

    private String message;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionInitiated initiatedBy;

    private UUID adminId;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
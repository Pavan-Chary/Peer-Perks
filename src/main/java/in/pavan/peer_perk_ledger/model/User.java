package in.pavan.peer_perk_ledger.model;

import in.pavan.peer_perk_ledger.enums.AccountStatus;
import in.pavan.peer_perk_ledger.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    // Transferable points
    private Integer allowanceBalance;

    // Users collected points
    private Integer redeemableBalance;
}
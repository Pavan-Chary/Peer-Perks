package in.pavan.peer_perk_ledger.repository;

import in.pavan.peer_perk_ledger.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.allowanceBalance = :points WHERE u.status='ACTIVE'")
    void resetAllAllowanceBalances(@Param("points") int points);

    @Query("SELECT u.id FROM User u WHERE u.status='ACTIVE'")
    Page<UUID> findAllActiveUserIds(Pageable pageable);

}

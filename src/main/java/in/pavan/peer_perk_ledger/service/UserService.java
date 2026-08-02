package in.pavan.peer_perk_ledger.service;

import in.pavan.peer_perk_ledger.model.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID userId);
    User getUserByEmail(String email);
    User deactivateUser(UUID userId);
    User updateUser(UUID userId, String name);
    boolean checkUserWithEmail(String email);
}

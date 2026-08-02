package in.pavan.peer_perk_ledger.service;

import in.pavan.peer_perk_ledger.enums.AccountStatus;
import in.pavan.peer_perk_ledger.exception.UserNotExistsException;
import in.pavan.peer_perk_ledger.model.User;
import in.pavan.peer_perk_ledger.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;


    @Override
    @Transactional
    public User getUserById(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new UserNotExistsException("No user exists with this user Id "+userId));
        return user;
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(()->new UserNotExistsException("No user exists with this email "+email));
        return user;
    }

    @Override
    @Transactional
    public User deactivateUser(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new UserNotExistsException("No user exists with this user Id "+userId));
        user.setStatus(AccountStatus.INACTIVE);
        userRepo.save(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(UUID userId, String name) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new UserNotExistsException("No user exists with this user Id "+userId));
        if(user.getName().equals(name))return user;
        user.setName(name);
        userRepo.save(user);
        return user;
    }

    @Override
    public boolean checkUserWithEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}

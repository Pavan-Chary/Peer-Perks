package in.pavan.peer_perk_ledger.service;

import in.pavan.peer_perk_ledger.constants.NotificationMessage;
import in.pavan.peer_perk_ledger.constants.TransactionConstants;
import in.pavan.peer_perk_ledger.enums.AccountStatus;
import in.pavan.peer_perk_ledger.enums.TransactionInitiated;
import in.pavan.peer_perk_ledger.enums.TransactionType;
import in.pavan.peer_perk_ledger.enums.UserRole;
import in.pavan.peer_perk_ledger.exception.*;
import in.pavan.peer_perk_ledger.model.ProductCatalog;
import in.pavan.peer_perk_ledger.model.Transaction;
import in.pavan.peer_perk_ledger.model.User;
import in.pavan.peer_perk_ledger.repository.ProductCatalogRepo;
import in.pavan.peer_perk_ledger.repository.TransactionRepo;
import in.pavan.peer_perk_ledger.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LedgerServiceImpl implements LedgerService{

    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;
    private final ProductCatalogRepo productCatalogRepo;
    private final NotificationService notificationService;


    @Override
    @Transactional
    public Transaction transferPoints(UUID senderId, UUID receiverId, int points, String message) {
        //user can not transfer points to themselves
        if(senderId.equals(receiverId)){
            throw new InvalidOperationException("User can not transfer points to themselves");
        }


        User receiver = userRepo.findById(receiverId)
                .orElseThrow(()->new UserNotExistsException("No user Exists with this user id "+receiverId));

        User sender = userRepo.findById(senderId)
                .orElseThrow(()->new UserNotExistsException("No user Exists with this user id "+senderId));

        if(receiver.getStatus()==AccountStatus.INACTIVE || sender.getStatus() == AccountStatus.INACTIVE){
            throw new UnauthorizedAccessException("User Both receiver and sender should be Active users");
        }
        if(sender.getAllowanceBalance()<points){
            throw new InsufficientPointsException("User do not have enough points to perform this transaction");
        }
        //create transaction
        Transaction transaction = new Transaction();
        transaction.setPoints(points);
        transaction.setMessage(message);
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setInitiatedBy(TransactionInitiated.USER);
        transaction.setType(TransactionType.PEER_TO_PEER);
        transaction = transactionRepo.save(transaction);

        //transfer from sender to receiver
        sender.setAllowanceBalance(sender.getAllowanceBalance()-points);
        userRepo.save(sender);
        receiver.setRedeemableBalance(receiver.getRedeemableBalance()+points);
        userRepo.save(receiver);

        notificationService.sendNotification(
                receiverId,
                NotificationMessage.getTransaferMsg(sender.getName(), points, message)
        );

        return transaction;
    }

    @Override
    @Transactional
    public Transaction redeemPoints(UUID userId, UUID adminId, Map<Long, Integer> orderItems) {
        if(userId.equals(adminId)){
            throw new InvalidOperationException("User can not redeem points to themselves");
        }
        if(orderItems.size()==0){
            throw new InvalidOperationException("Order should have some products");
        }
        User user = userRepo.findById(userId)
                .orElseThrow(()->new UserNotExistsException("No user Exists with this user id "+userId));

        User admin = userRepo.findById(adminId)
                .orElseThrow(()->new UserNotExistsException("No user Exists with this user id "+adminId));

        if(admin.getRole()==UserRole.ROLE_USER){
            throw new UnauthorizedAccessException("User can not redeem points");
        }
        if(admin.getStatus() == AccountStatus.INACTIVE || user.getStatus() == AccountStatus.INACTIVE){
            throw new UnauthorizedAccessException("Inactive users can not redeem points");
        }
        //Thinking to create OrderItems model to store all the items that are soled in a particular order
        Transaction transaction = new Transaction();
        int totalPoints = 0;
        for(Long productId : orderItems.keySet()){
            ProductCatalog product = productCatalogRepo.findById(productId)
                    .orElseThrow(()-> new ProductNotExistsEception("No product exists with this Id "+productId));
            totalPoints += orderItems.get(productId)*product.getPointsRequired();
        }
        if(user.getRedeemableBalance()<totalPoints){
            throw new InsufficientPointsException("User do not have sufficient points to perform this transaction");
        }

        transaction.setPoints(totalPoints);
        transaction.setType(TransactionType.OFFLINE_REDEMPTION);
        transaction.setMessage("User Redeemed at the Office store");
        transaction.setReceiverId(userId);
        transaction.setAdminId(adminId);
        transaction.setInitiatedBy(TransactionInitiated.USER);

        transaction = transactionRepo.save(transaction);

        user.setRedeemableBalance(user.getRedeemableBalance()-totalPoints);

        userRepo.save(user);

        return transaction;

    }

    @Override
    @Transactional
    public Transaction monthlyResetPoints(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new UserNotExistsException("No user exists with this user Id "+userId));
        if(user.getStatus() == AccountStatus.INACTIVE){
            throw new UnauthorizedAccessException("Can not add points to inactive users");
        }
        user.setAllowanceBalance(TransactionConstants.ADD_POINTS);

        Transaction transaction = new Transaction();
        transaction.setPoints(TransactionConstants.ADD_POINTS);
        transaction.setType(TransactionType.WALLET_RESET);
        transaction.setInitiatedBy(TransactionInitiated.SYSTEM);
        transaction.setReceiverId(userId);

        transactionRepo.save(transaction);
        userRepo.save(user);

        return transaction;

    }
}

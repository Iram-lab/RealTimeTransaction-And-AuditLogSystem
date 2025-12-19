package com.example.backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.TransferRequest;
import com.example.backend.models.TransactionHistory;
import com.example.backend.models.User;
import com.example.backend.repository.TransactionHistoryRepository;
import com.example.backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private static final Logger logger =
            LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransactionHistoryRepository txnRepo;

    // ===================== TRANSFER =====================
    @Transactional
    public ApiResponse transfer(TransferRequest request) {
        try {
            logger.info("Transfer request | senderId={} receiverId={} amount={}",
                    request.getSenderId(),
                    request.getReceiverId(),
                    request.getAmount());

            if (request.getAmount() <= 0) {
                logger.warn("Invalid transfer amount: {}", request.getAmount());
                return new ApiResponse(false, "Transfer amount must be greater than zero");
            }

            Optional<User> senderOpt =
                    userRepo.findById(request.getSenderId());
            Optional<User> receiverOpt =
                    userRepo.findById(request.getReceiverId());

            if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
                logger.warn("User not found | senderId={} receiverId={}",
                        request.getSenderId(), request.getReceiverId());
                return new ApiResponse(false, "Sender or receiver not found");
            }

            User sender = senderOpt.get();
            User receiver = receiverOpt.get();

            logger.info("Sender balance before: {}", sender.getAmount());
            logger.info("Receiver balance before: {}", receiver.getAmount());

            if (sender.getAmount() < request.getAmount()) {
                logger.warn("Insufficient funds | senderId={} balance={} requested={}",
                        sender.getId(), sender.getAmount(), request.getAmount());
                return new ApiResponse(false, "Insufficient funds");
            }

            // Update balances
            sender.setAmount((int) (sender.getAmount() - request.getAmount()));
            receiver.setAmount((int) (receiver.getAmount() + request.getAmount()));

            userRepo.save(sender);
            userRepo.save(receiver);

            // Save transaction
            TransactionHistory txn = new TransactionHistory();
            txn.setSenderId(sender.getId());
            txn.setReceiverId(receiver.getId());
            txn.setAmount(request.getAmount());
            txn.setStatus("SUCCESS");
            txn.setTimestamp(LocalDateTime.now());

            txnRepo.save(txn);

            logger.info("Transfer successful | txnId={} senderId={} receiverId={}",
                    txn.getId(), sender.getId(), receiver.getId());

            return new ApiResponse(true, "Transfer successful");

        } catch (Exception e) {
            logger.error("Transfer failed due to server error", e);
            return new ApiResponse(false, "Transfer failed due to server error");
        }
    }

    // ===================== TRANSACTION HISTORY =====================
    public List<TransactionHistory> getHistory(Long userId) {
        try {
            logger.info("Fetching transaction history | userId={}", userId);

            List<TransactionHistory> history =
                    txnRepo.findBySenderIdOrReceiverId(userId, userId);

            logger.info("History fetched | userId={} records={}",
                    userId, history.size());

            return history;

        } catch (Exception e) {
            logger.error("Failed to fetch transaction history | userId={}", userId, e);
            throw new RuntimeException("Unable to fetch transaction history");
        }
    }
}

package com.example.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.dto.ApiResponse;
import com.example.backend.dto.TransferRequest;
import com.example.backend.models.TransactionHistory;
import com.example.backend.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger =
            LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService txnService;

    // ===================== TRANSFER =====================
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse> transfer(
            @RequestBody TransferRequest request) {

        try {
            logger.info("POST /api/transactions/transfer | senderId={} receiverId={} amount={}",
                    request.getSenderId(),
                    request.getReceiverId(),
                    request.getAmount());

            ApiResponse response = txnService.transfer(request);

            if (!response.isSuccess()) {
                logger.warn("Transfer failed | {}", response.getMessage());
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response);
            }

            logger.info("Transfer successful | {}", response.getMessage());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Transfer error", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal server error"));
        }
    }

    // ===================== TRANSACTION HISTORY =====================
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getHistory(@PathVariable Long userId) {

        try {
            logger.info("GET /api/transactions/history/{} | Fetching history", userId);

            List<TransactionHistory> history = txnService.getHistory(userId);

            if (history.isEmpty()) {
                logger.warn("No history found for userId={}", userId);
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build();
            }

            logger.info("History fetched | userId={} totalRecords={}",
                    userId, history.size());

            return ResponseEntity.ok(history);

        } catch (Exception e) {
            logger.error("Error fetching history for userId={}", userId, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch transaction history");
        }
    }
}

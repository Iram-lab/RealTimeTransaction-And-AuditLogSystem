package com.example.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.TransactionHistory;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}

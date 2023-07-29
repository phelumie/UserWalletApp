package com.ajisegiri.userwallet.repo;

import com.ajisegiri.userwallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}

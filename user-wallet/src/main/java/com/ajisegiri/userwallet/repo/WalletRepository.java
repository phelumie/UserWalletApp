package com.ajisegiri.userwallet.repo;

import com.ajisegiri.userwallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    @Modifying
    @Query("UPDATE Wallet w SET w.balance = w.balance + :amount WHERE w.walletId = :walletId")
    void updateBalance(@Param("walletId") UUID walletId, @Param("amount") BigDecimal amount);


}

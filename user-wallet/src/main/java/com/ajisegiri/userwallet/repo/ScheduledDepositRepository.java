package com.ajisegiri.userwallet.repo;

import com.ajisegiri.userwallet.model.ScheduledDeposit;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ScheduledDepositRepository extends JpaRepository<ScheduledDeposit,Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT sd FROM ScheduledDeposit sd WHERE (sd.endDate >= CURRENT_DATE OR sd.endDate IS NULL) AND sd.nextScheduledDate = CURRENT_DATE AND sd.isActive = true")
    List<ScheduledDeposit> findActiveScheduledDeposits();



}

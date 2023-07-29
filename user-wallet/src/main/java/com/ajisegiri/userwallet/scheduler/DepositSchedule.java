package com.ajisegiri.userwallet.scheduler;

import com.ajisegiri.userwallet.dto.NotificationPayload;
import com.ajisegiri.userwallet.enums.Frequency;
import com.ajisegiri.userwallet.enums.NotificationType;
import com.ajisegiri.userwallet.enums.TransactionStatus;
import com.ajisegiri.userwallet.enums.TransactionType;
import com.ajisegiri.userwallet.model.Beneficiary;
import com.ajisegiri.userwallet.model.ScheduledDeposit;
import com.ajisegiri.userwallet.model.Transaction;
import com.ajisegiri.userwallet.model.Wallet;
import com.ajisegiri.userwallet.repo.ScheduledDepositRepository;
import com.ajisegiri.userwallet.repo.TransactionRepository;
import com.ajisegiri.userwallet.repo.WalletRepository;
import com.ajisegiri.userwallet.service.EventPublisher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.ajisegiri.userwallet.config.RabbitMQDirectConfig.NOTIFICATION_QUEUE;

@Component
@Transactional
@RequiredArgsConstructor
public class DepositSchedule {
    private final ScheduledDepositRepository scheduledDepositRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final EventPublisher<NotificationPayload> eventPublisher;

    private static final String SUCCESS_MESSAGE = """ 
            Dear %s,
                        
            We are pleased to inform you that your transaction has been successfully processed. Below are the details of your transaction:
        
            Transaction ID: %s
            Payment Amount: NGN %s
            If you have any questions or need further assistance regarding your transaction, please don't hesitate to contact our customer support team.
                        
            Thank you for choosing our services/products. We appreciate your business.
                        
            Best regards,
            [Your Company Name]
            """;
    private static final String INSUFFICIENT_MESSAGE = """
        Dear %s,
        
        We regret to inform you that there was an insufficient funds issue for your recent transaction with ID %s.
        The amount of %s could not be processed due to insufficient funds in your account.
        
        Please ensure that you have sufficient funds in your account to avoid any inconveniences in the future.
        
        Thank you for your understanding.
        
        Best regards,
        [Your Company Name]
        """;
    private static final String SUBJECT = "Insufficient Funds: Transaction ID %s";


    //        @Scheduled(cron = "0 0 12  * * ?")
    @Scheduled(fixedRate = 10000L)
    public void ScheduledDeposit() {
        List<ScheduledDeposit> activeScheduledDeposits = scheduledDepositRepository.findActiveScheduledDeposits();

        Set<Wallet> depositsByWalletId = activeScheduledDeposits.stream()
                .collect(Collectors.groupingBy(deposit -> deposit.getWallet())).keySet();

        depositsByWalletId.forEach(this::processDepositsForWallet);

    }
    private void processDepositsForWallet(Wallet wallet) {
        var deposits=wallet.getScheduledDeposits();
        BigDecimal totalAmount = calculateTotalAmount(deposits);


        // insufficient funds
        if (wallet.getBalance().compareTo(totalAmount) < 0) {

            for (ScheduledDeposit deposit : deposits) {
                deposit.setNextScheduledDate(calculateNextScheduledDate(deposit.getFrequency()));
            }
            var saveDeposits= saveTransactions(deposits,TransactionStatus.ERROR);
            publishEvent(saveDeposits,TransactionStatus.ERROR);
            return;
        }

        for (ScheduledDeposit deposit : deposits) {
            processDeposit(deposit);
            deposit.setNextScheduledDate(calculateNextScheduledDate(deposit.getFrequency()));
        }

        wallet.setBalance(wallet.getBalance().subtract(totalAmount));


        var saveDeposits= saveTransactions(deposits,TransactionStatus.SUCCESS);
        publishEvent(saveDeposits,TransactionStatus.SUCCESS);



    }
    private void publishEvent(List<Transaction> transactions,TransactionStatus status){
        var wallet=transactions.get(0).getSourceWallet();
        transactions.forEach(t->{
            String message;
            String subject;
            var transactionId=t.getTransactionId();
            if (status.equals(TransactionStatus.SUCCESS)){
                message=SUCCESS_MESSAGE.formatted("%s", transactionId, t.getAmount());
                subject="Payment Confirmation: Transaction ID ".concat(String.valueOf(transactionId));
            }
            else {
                message=INSUFFICIENT_MESSAGE.formatted("%s", transactionId, t.getAmount());
                subject=("Insufficient Funds: Transaction ID ").concat(String.valueOf(transactionId));

            }

            eventPublisher.publish(generatePayload(wallet.getUserId(),NotificationType.EMAIL, message, subject), NOTIFICATION_QUEUE);

        });
    }



    @Transactional
    private List<Transaction> saveTransactions(List<ScheduledDeposit> deposits, TransactionStatus status) {
        List<Transaction> transaction=new ArrayList<>();
        deposits.forEach(deposit->{
            Transaction t=new Transaction();
            t.setSourceWallet(deposit.getWallet());
            t.setTransactionType(TransactionType.DEBIT);
            t.setAmount(deposit.getAmount());
            t.setTransactionStatus(status);
            t.setTimestamp(LocalDateTime.now());
            t.setScheduledDeposit(deposit);
            transaction.add(t);


    });

        return transactionRepository.saveAll(transaction);

    }
    private void processDeposit(ScheduledDeposit scheduledDeposit) {
        BigDecimal depositAmount = scheduledDeposit.getAmount();
        List<Beneficiary> beneficiaries = scheduledDeposit.getBeneficiariesASList();

        for (Beneficiary beneficiary : beneficiaries) {
            UUID beneficiaryWalletId = UUID.fromString(beneficiary.getWalletId());
            walletRepository.updateBalance(beneficiaryWalletId, depositAmount);
        }

    }
//    private BigDecimal acalculateTotalAmount(List<ScheduledDeposit> deposits) {
//        BigDecimal totalAmount = BigDecimal.ZERO;
//        for (ScheduledDeposit deposit : deposits) {
//            BigDecimal depositAmount = deposit.getAmount();
//            List<Beneficiary> beneficiaries = deposit.getBeneficiariesASList();
//            int numBeneficiaries = beneficiaries.size();
//            totalAmount = totalAmount.add(depositAmount.multiply(BigDecimal.valueOf(numBeneficiaries)));
//        }
//        return totalAmount;
//    }
    private BigDecimal calculateTotalAmount(List<ScheduledDeposit> deposits) {
        return deposits.stream()
                .map(deposit -> deposit.getAmount().multiply(BigDecimal.valueOf(deposit.getBeneficiariesASList().size())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private LocalDate calculateNextScheduledDate(Frequency frequency) {
        if (frequency.equals(Frequency.DAILY))
            return LocalDate.now().plusDays(1);
        if (frequency.equals(Frequency.WEEKLY))
            return LocalDate.now().plusWeeks(1);
        if (frequency.equals(Frequency.MONTHLY))
            return LocalDate.now().plusMonths(1);

        return LocalDate.now().plusYears(1);

    }


    public NotificationPayload generatePayload(String userId, NotificationType notificationType,String message, String subject){
        NotificationPayload payload = new NotificationPayload();
        payload.setUserId(userId);
        payload.setMessage(message);
        payload.setSubject(subject);
        payload.setNotificationType(notificationType);
        return  payload;
    }

}
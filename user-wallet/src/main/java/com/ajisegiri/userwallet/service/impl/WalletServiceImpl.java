package com.ajisegiri.userwallet.service.impl;

import com.ajisegiri.userwallet.dto.CreateWallet;
import com.ajisegiri.userwallet.model.Wallet;
import com.ajisegiri.userwallet.repo.WalletRepository;
import com.ajisegiri.userwallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public void createWallet(CreateWallet createWallet){
        var wallet=new Wallet();

        wallet.setWalletId(UUID.randomUUID());
        wallet.setUserId(createWallet.getUserId());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency(createWallet.getCurrency());

        walletRepository.save(wallet);
    }

    @Override
    public List<Wallet> getAllWallets(){
        return walletRepository.findAll();
    }

}

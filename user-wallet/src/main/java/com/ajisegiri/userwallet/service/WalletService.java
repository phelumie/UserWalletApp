package com.ajisegiri.userwallet.service;

import com.ajisegiri.userwallet.dto.CreateWallet;
import com.ajisegiri.userwallet.model.Wallet;

import java.util.List;

public interface WalletService {
    void createWallet(CreateWallet createWallet);

    List<Wallet> getAllWallets();
}

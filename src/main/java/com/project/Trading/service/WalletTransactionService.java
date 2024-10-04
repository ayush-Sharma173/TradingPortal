package com.project.Trading.service;

import com.project.Trading.domain.WalletTransactionType;
import com.project.Trading.model.Wallet;
import com.project.Trading.model.WalletTransaction;

import java.util.List;

public interface WalletTransactionService {
    WalletTransaction createTransaction(Wallet wallet,
                                        WalletTransactionType type,
                                        String transferId,
                                        String purpose,
                                        Long amount
    );

    List<WalletTransaction> getTransactions(Wallet wallet, WalletTransactionType type);

}

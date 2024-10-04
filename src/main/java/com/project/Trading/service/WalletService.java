package com.project.Trading.service;

import com.project.Trading.exception.WalletException;
import com.project.Trading.model.Order;
import com.project.Trading.model.User;
import com.project.Trading.model.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user) throws WalletException;

    public Wallet addBalanceToWallet(Wallet wallet, Long money) throws WalletException;

    public Wallet findWalletById(Long id) throws WalletException;

    public Wallet walletToWalletTransfer(User sender,Wallet receiverWallet, Long amount) throws WalletException;

    public Wallet payOrderPayment(Order order, User user) throws WalletException;



}

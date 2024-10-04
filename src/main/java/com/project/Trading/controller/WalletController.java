package com.project.Trading.controller;

import com.project.Trading.domain.WalletTransactionType;
import com.project.Trading.model.*;
import com.project.Trading.response.PaymentResponse;
import com.project.Trading.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;


    @Autowired
    private OrderService orderService;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @Autowired
    private PaymentService paymentService;


    @GetMapping("")
    public ResponseEntity<?> getUserWallet(@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<WalletTransaction>> getWalletTransaction(
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        List<WalletTransaction> transactions=walletTransactionService.getTransactions(wallet,null);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PutMapping("/deposit/amount/{amount}")
    public ResponseEntity<PaymentResponse> depositMoney(@RequestHeader("Authorization")String jwt,
                                                        @PathVariable Long amount) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);
//        PaymentResponse res = walleteService.depositFunds(user,amount);
        PaymentResponse res = new PaymentResponse();
        res.setPayment_url("deposite success");
        walletService.addBalanceToWallet(wallet, amount);

        return new ResponseEntity<>(res,HttpStatus.OK);

    }

    @PutMapping("/deposit")
    public ResponseEntity<Wallet> addMoneyToWallet(
            @RequestHeader("Authorization")String jwt,
            @RequestParam(name="order_id") Long orderId,
            @RequestParam(name="payment_id")String paymentId
    ) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        Wallet wallet = walletService.getUserWallet(user);


        PaymentOrder order = paymentService.getPaymentOrderById(orderId);
        Boolean status=paymentService.ProccedPaymentOrder(order,paymentId);
        PaymentResponse res = new PaymentResponse();
        res.setPayment_url("deposite success");

        if(status){
            wallet=walletService.addBalanceToWallet(wallet, order.getAmount());
        }


        return new ResponseEntity<>(wallet,HttpStatus.OK);

    }

//    @PutMapping("/withdraw/amount/{amount}/user/{userId}")
//    public ResponseEntity<PaymentResponse> withdrawMoney(@PathVariable Long userId, @PathVariable Long amount) throws Exception {
//
//        String wallet = walletService.depositFunds(userId,amount);
//
//        return new ResponseEntity<>(wallet,HttpStatus.OK);
//    }

    @PutMapping("/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization")String jwt,
                                                         @PathVariable Long walletId,
                                                         @RequestBody WalletTransaction req
    ) throws Exception {
        User senderUser =userService.findUserProfileByJwt(jwt);


        Wallet reciverWallet = walletService.findWalletById(walletId);

        Wallet wallet = walletService.walletToWalletTransfer(senderUser,reciverWallet, req.getAmount());
        WalletTransaction walletTransaction=walletTransactionService.createTransaction(
                wallet,
                WalletTransactionType.WALLET_TRANSFER,reciverWallet.getId().toString(),
                req.getPurpose(),
                -req.getAmount()
        );

        return new ResponseEntity<>(wallet,HttpStatus.OK);

    }


    @PutMapping("/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@PathVariable Long orderId,
                                                  @RequestHeader("Authorization")String jwt) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        System.out.println("-------- "+orderId);
        Order order=orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order,user);

        return new ResponseEntity<>(wallet,HttpStatus.OK);

    }
}

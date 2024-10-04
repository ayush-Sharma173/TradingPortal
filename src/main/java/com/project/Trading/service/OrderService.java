package com.project.Trading.service;

import com.project.Trading.domain.OrderType;
import com.project.Trading.model.Coin;
import com.project.Trading.model.Order;
import com.project.Trading.model.OrderItem;
import com.project.Trading.model.User;
import com.project.Trading.request.CreateOrderRequest;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId);

    List<Order> getAllOrdersForUser(Long userId, String orderType,String assetSymbol);

    void cancelOrder(Long orderId);

//    Order buyAsset(CreateOrderRequest req, Long userId, String jwt) throws Exception;

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

//    Order sellAsset(CreateOrderRequest req,Long userId,String jwt) throws Exception;


}

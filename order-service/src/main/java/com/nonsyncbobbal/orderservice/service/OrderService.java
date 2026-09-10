package com.nonsyncbobbal.orderservice.service;

import com.nonsyncbobbal.orderservice.dto.OrderRequest;
import com.nonsyncbobbal.orderservice.model.Order;
import com.nonsyncbobbal.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        // Map order request to order entity
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setSkuCode(orderRequest.skuCode());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        // Save order entity to the database
        orderRepository.save(order);
        log.info("Order placed successfully: {}", order.getOrderNumber());


    }
}

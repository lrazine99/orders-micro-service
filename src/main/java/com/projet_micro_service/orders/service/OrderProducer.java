package com.projet_micro_service.orders.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.projet_micro_service.orders.config.RabbitMQConfig;
import com.projet_micro_service.orders.model.OrderModel;

@Service
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${order.routing-key}")
    private String orderRoutingKey;

    public void publishOrderCreated(OrderModel order) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, orderRoutingKey, order);
    }

    public void publishStockReduction(Integer productId, Integer quantity) {
        String message = String.format("{\"productId\": %d, \"quantity\": %d}", productId, quantity);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "product.stock.reduce", message);
    }
}
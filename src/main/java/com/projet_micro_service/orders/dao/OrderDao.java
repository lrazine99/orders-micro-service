package com.projet_micro_service.orders.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet_micro_service.orders.model.OrderModel;

@Service
public class OrderDao {
    @Autowired
    private OrderRepositoryInterface productRepository;

    public List<OrderModel> findAll() {
        return productRepository.findAll();
    }
}

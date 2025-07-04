package com.projet_micro_service.orders.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet_micro_service.orders.model.OrderModel;

@Service
public class OrderDao {
    @Autowired
    private OrderRepositoryInterface orderRepository;

    public List<OrderModel> findAll() {
        return orderRepository.findAll();
    }

    public OrderModel findById(int id) {
        return orderRepository.findById(id);
    }

    public List<OrderModel> findByUserId(int userId) {
        return orderRepository.findByUserId(userId);
    }

    public OrderModel save(OrderModel order) {
        return orderRepository.save(order);
    }

    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return orderRepository.existsById(id);
    }
}

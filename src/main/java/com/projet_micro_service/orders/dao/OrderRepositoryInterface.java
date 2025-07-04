package com.projet_micro_service.orders.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet_micro_service.orders.model.OrderModel;

@Repository
public interface OrderRepositoryInterface extends JpaRepository<OrderModel, Integer> {
    OrderModel findById(int id);

    void deleteById(int id);
}

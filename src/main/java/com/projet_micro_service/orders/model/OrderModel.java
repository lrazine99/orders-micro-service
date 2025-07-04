package com.projet_micro_service.orders.model;

import java.util.List;
import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "orders")
@Entity
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String details;
    private int totalPrice;
    private int userId;
    
    @ElementCollection
    private List<Integer> productIds;
    
    @ElementCollection
    private Map<Integer, Integer> productQuantities;

    public OrderModel() {
    }

    public OrderModel(int id, String details, int totalPrice, int userId, List<Integer> productIds, Map<Integer, Integer> productQuantities) {
        this.id = id;
        this.details = details;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.productIds = productIds;
        this.productQuantities = productQuantities;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getProductIds() {
        return this.productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }

    public Map<Integer, Integer> getProductQuantities() {
        return this.productQuantities;
    }

    public void setProductQuantities(Map<Integer, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }
}

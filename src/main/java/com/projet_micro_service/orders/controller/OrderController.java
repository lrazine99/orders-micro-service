package com.projet_micro_service.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projet_micro_service.orders.dao.OrderDao;
import com.projet_micro_service.orders.model.OrderModel;

@RestController
public class OrderController {

    @Autowired
    private final OrderDao orderDao;

    public OrderController(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @GetMapping("/orders")
    public List<OrderModel> getOrders() {
        System.out.println("Fetching all orders" + orderDao.findAll());
        return orderDao.findAll();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable int id) {
        OrderModel order = orderDao.findById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderModel> createOrder(@RequestBody OrderModel order) {
        OrderModel savedOrder = orderDao.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderModel> updateOrder(@PathVariable int id, @RequestBody OrderModel order) {
        if (!orderDao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        order.setId(id);
        OrderModel updatedOrder = orderDao.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/orders/client/{clientId}")
    public List<OrderModel> getOrdersByClientId(@PathVariable int clientId) {
        return orderDao.findByUserId(clientId);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        if (!orderDao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderDao.deleteById(id);
        return ResponseEntity.ok("{\"message\":\"Order with id " + id + " deleted\", \"status\":200}");
    }
}

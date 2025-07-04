package com.projet_micro_service.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String getOrderById(@PathVariable int id) {
        return "{\"message\":\"Hello World bonjour " + id + "\", \"status\":200}";
    }

    @DeleteMapping("/orders/{id}")
    public String deleteOrder(@PathVariable int id) {
        // Here you would typically call a service to delete the order by id
        return "{\"message\":\"Order with id " + id + " deleted\", \"status\":200}";
    }

    // @PostMapping("/orders")
    // public OrderModel createOrder(@RequestBody OrderModel order) {
    // return orderService.createOrder(order);
    // }
}

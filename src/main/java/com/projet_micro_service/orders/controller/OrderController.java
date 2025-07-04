package com.projet_micro_service.orders.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.client.RestTemplate;

import com.projet_micro_service.orders.dao.OrderDao;
import com.projet_micro_service.orders.model.OrderModel;
import com.projet_micro_service.orders.service.OrderProducer;

@RestController
public class OrderController {

    @Autowired
    private final OrderDao orderDao;
    
    @Autowired
    private final RestTemplate restTemplate;
    
    @Autowired
    private OrderProducer orderProducer;

    public OrderController(OrderDao orderDao, RestTemplate restTemplate) {
        this.orderDao = orderDao;
        this.restTemplate = restTemplate;
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
    public ResponseEntity<?> createOrder(@RequestBody OrderModel order) {
        // Check stock availability for each product
        if (order.getProductQuantities() != null) {
            for (Map.Entry<Integer, Integer> entry : order.getProductQuantities().entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();
                
                try {
                    String checkStockUrl = "http://localhost:8082/products/" + productId + "/check-stock/" + quantity;
                    String response = restTemplate.getForObject(checkStockUrl, String.class);
                    
                    if (response == null || !response.contains("\"available\":true")) {
                        return ResponseEntity.badRequest()
                            .body("{\"message\":\"Insufficient stock for product " + productId + "\", \"status\":400}");
                    }
                } catch (Exception e) {
                    return ResponseEntity.badRequest()
                        .body("{\"message\":\"Error checking stock for product " + productId + "\", \"status\":400}");
                }
            }
            
            // Reduce stock for each product
            for (Map.Entry<Integer, Integer> entry : order.getProductQuantities().entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();
                
                try {
                    String reduceStockUrl = "http://localhost:8082/products/" + productId + "/reduce-stock";
                    restTemplate.postForObject(reduceStockUrl, quantity, String.class);
                } catch (Exception e) {
                    return ResponseEntity.badRequest()
                        .body("{\"message\":\"Error reducing stock for product " + productId + "\", \"status\":400}");
                }
            }
        }
        
        OrderModel savedOrder = orderDao.save(order);
        
        // Publish order created event
        orderProducer.publishOrderCreated(savedOrder);
        
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

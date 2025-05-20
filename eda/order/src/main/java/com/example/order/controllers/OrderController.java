package com.example.order.controllers;

//import com.example.order.jwt.CustomJwt;
import com.example.order.models.Order;
import com.example.order.rabbitmq.RabbitMQConsumer;
import com.example.order.rabbitmq.RabbitMQProducer;
import com.example.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitMQProducer producer;

    @Autowired
    private RabbitMQConsumer consumer;

    // RabbitMQ
    @PostMapping("/rabbitmq/producer")
    public String sendMessage(@RequestParam String message) {
        producer.sendMessage(message);
        return "Message sent: " + message;
    }

    @GetMapping("/rabbitmq/consumer")
    public String receiveMessage(@RequestParam String message) {
        consumer.receiveMessage(message);
        return "Message received: " + message;
    }

    // Auth
//    @GetMapping("/user")
//    @PreAuthorize("hasRole('client_user')")
//    public String hello() {
//        return "Hello from Spring boot & Keycloak";
//    }
//
//    @GetMapping("/admin")
//    @PreAuthorize("hasRole('client_admin')")
//    public String hello2() {
//        return "Hello from Spring boot & Keycloak - ADMIN";
//    }

    // Orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Order order) {
        try {
            return ResponseEntity.ok(orderService.updateOrder(id, order));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.order;

import com.example.order.models.Order;
import com.example.order.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final OrderService orderService;

    public DatabaseInitializer(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) {
        if (!mongoTemplate.collectionExists(Order.class)) {
            mongoTemplate.createCollection(Order.class);
            System.out.println("La collection 'orders' a été créée.");
        }
        orderService.DatabaseSeed();
    }
}

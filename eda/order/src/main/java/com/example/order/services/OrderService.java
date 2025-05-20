package com.example.order.services;

import com.example.order.models.Order;
import com.example.order.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static final String ORDER_CACHE_KEY = "order:";
    private static final String ORDERS_CACHE_KEY = "orders";
    private static final long CACHE_TTL = 3600; // 1 heure en secondes

    private final OrderRepository orderRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository, RedisTemplate<String, Object> redisTemplate) {
        this.orderRepository = orderRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Order> getAllOrders() {
        logger.info("Get all orders request");

        // Vérifier si la liste des commandes est en cache
        @SuppressWarnings("unchecked")
        List<Order> cachedOrders = (List<Order>) redisTemplate.opsForValue().get(ORDERS_CACHE_KEY);

        if (cachedOrders != null) {
            logger.info("Returning orders from cache");
            return cachedOrders;
        }

        // Si non trouvé dans le cache, récupérer depuis la base de données
        List<Order> orders = orderRepository.findAll();

        // Mettre en cache le résultat
        redisTemplate.opsForValue().set(ORDERS_CACHE_KEY, orders, CACHE_TTL, TimeUnit.SECONDS);
        logger.info("Cached orders list");

        return orders;
    }

    @Cacheable(value = "orders", key = "'order:' + #id", unless = "#result.isEmpty()")
    public Optional<Order> getOrderById(String id) {
        logger.info("Get order by ID: {}", id);
        return orderRepository.findById(id);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "orders", key = "'orders'")
            }
    )
    public Order createOrder(Order order) {
        logger.info("Creating order request");
        Order savedOrder = orderRepository.save(order);

        // Mettre la nouvelle commande en cache
        String cacheKey = ORDER_CACHE_KEY + savedOrder.getId();
        redisTemplate.opsForValue().set(cacheKey, Optional.of(savedOrder), CACHE_TTL, TimeUnit.SECONDS);

        // Invalider le cache de la liste des commandes
        redisTemplate.delete(ORDERS_CACHE_KEY);

        logger.info("Created order with ID: {}", savedOrder.getId());
        return savedOrder;
    }

    @Caching(
            put = {
                    @CachePut(value = "orders", key = "'order:' + #id")
            },
            evict = {
                    @CacheEvict(value = "orders", key = "'orders'")
            }
    )
    public Order updateOrder(String id, Order order) {
        logger.info("Updating order with ID: {}", id);
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            order.setId(id);
            Order updatedOrder = orderRepository.save(order);

            // Mettre à jour le cache
            String cacheKey = ORDER_CACHE_KEY + id;
            redisTemplate.opsForValue().set(cacheKey, Optional.of(updatedOrder), CACHE_TTL, TimeUnit.SECONDS);

            // Invalider le cache de la liste des commandes
            redisTemplate.delete(ORDERS_CACHE_KEY);

            logger.info("Updated order with ID: {}", id);
            return updatedOrder;
        } else {
            logger.error("Order not found with ID: {}", id);
            throw new RuntimeException("Order not found");
        }
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "orders", key = "'order:' + #id"),
                    @CacheEvict(value = "orders", key = "'orders'")
            }
    )
    public void deleteOrder(String id) {
        logger.info("Deleting order with ID: {}", id);
        orderRepository.deleteById(id);

        // S'assurer que la commande est retirée du cache
        String cacheKey = ORDER_CACHE_KEY + id;
        redisTemplate.delete(cacheKey);

        // Invalider le cache de la liste des commandes
        redisTemplate.delete(ORDERS_CACHE_KEY);

        logger.info("Deleted order with ID: {}", id);
    }

    public void DatabaseSeed() {
        if (orderRepository.count() == 0) {
            List<Order> initialOrders = List.of(
                    new Order("John Doe", "Laptop", 1, 999.99f, "Pending"),
                    new Order("Jane Smith", "Smartphone", 2, 1599.98f, "Shipped"),
                    new Order("Alice Johnson", "Desk Chair", 1, 149.99f, "Delivered"),
                    new Order("Bob Brown", "Suit navy", 3, 449.97f, "Pending"),
                    new Order("Charlie White", "Tablet", 1, 299.99f, "In Progress")
            );
            List<Order> savedOrders = orderRepository.saveAll(initialOrders);

            // Mettre en cache les commandes initialisées
            for (Order order : savedOrders) {
                String cacheKey = ORDER_CACHE_KEY + order.getId();
                redisTemplate.opsForValue().set(cacheKey, Optional.of(order), CACHE_TTL, TimeUnit.SECONDS);
            }

            // Mettre en cache la liste complète
            redisTemplate.opsForValue().set(ORDERS_CACHE_KEY, savedOrders, CACHE_TTL, TimeUnit.SECONDS);

            System.out.println("La base de données a été initialisée avec les commandes par défaut.");
        } else {
            System.out.println("Les commandes existent déjà dans la base de données.");
        }
    }

    // Supprime toutes les entrées de cache liées aux commandes
    private void evictAllOrdersCache() {
        Set<String> keys = redisTemplate.keys(ORDER_CACHE_KEY + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
        redisTemplate.delete(ORDERS_CACHE_KEY);
    }
}
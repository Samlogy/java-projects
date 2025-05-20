package com.example.order.rabbitmq;

import com.example.order.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void receiveMessage(String message) {
        System.out.println("Received received : " + message);

        if (message.contains("fail")) {
            throw new RuntimeException("Échec du traitement !");
        }

        System.out.println("Commande traitée avec succès : " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.DLQ)
    public void handleDLQ(String failedOrder) {
        System.out.println("Commande envoyée en DLQ : " + failedOrder);
    }
}
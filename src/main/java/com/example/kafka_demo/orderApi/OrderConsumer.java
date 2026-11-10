package com.example.kafka_demo.orderApi;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "orders-topic", groupId ="order-group")
    @Transactional
    public void consumerOrder(String message){

        log.info("Received Order Request from Kafka: {}", message);
        try {
            Long orderId = Long.parseLong(message.trim());

            orderRepository.findById(orderId).ifPresent(order -> {
                order.setId(orderId);
                order.setStatus("COMPLETED");
                log.info("Order ID: {} successfully processed and status updated to COMPLETED", orderId);

            });

        } catch (Exception e) {
            log.error("Error while processing Order Request from Kafka: {}", message, e);
        }

    }
}

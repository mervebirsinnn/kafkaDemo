package com.example.kafka_demo.orderApi;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderRepository repository;


    @Transactional
    public Order create(CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.customerName());
        order.setTotalAmount(request.totalAmount());

        Order save = repository.save(order);
        System.out.println("Kaydedilen sipariş id: " + save.getId());
        kafkaTemplate.send("orders-topic", save.getId().toString());

        return save;
    }


    @Transactional(readOnly = true)
    public Order find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
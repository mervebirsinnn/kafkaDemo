package com.example.kafka_demo.orderApi;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Sipariş bulunamadı: " + id);
    }
}
package com.example.kafka_demo.orderApi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Order> create(
            @Valid @RequestBody CreateOrderRequest request) {

        Order order = service.create(request);

        return ResponseEntity
                .created(URI.create("/orders/" + order.getId()))
                .body(order);
    }

    @GetMapping("/{id}")
    public Order find(@PathVariable Long id) {
        return service.find(id);
    }
}

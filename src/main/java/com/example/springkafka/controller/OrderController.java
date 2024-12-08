package com.example.springkafka.controller;

import com.example.springkafka.dto.CreateOrderRequest;
import com.example.springkafka.dto.UpdateOrderRequest;
import com.example.springkafka.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
        orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully!");
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderId,
                                              @RequestBody UpdateOrderRequest request) {
        request.setOrderId(orderId);
        orderService.updateOrder(request);
        return ResponseEntity.ok("Order updated successfully!");
    }
}

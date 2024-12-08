package com.example.springkafka.service;

import com.example.springkafka.domain.Order;
import com.example.springkafka.dto.CreateOrderRequest;
import com.example.springkafka.dto.UpdateOrderRequest;
import com.example.springkafka.event.OrderCreatedEvent;
import com.example.springkafka.event.OrderUpdatedEvent;
import com.example.springkafka.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final EventService eventService;

    public void createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setStatus("CREATED");
        order.setAmount(request.getAmount());
        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(savedOrder.getId().toString(), "CREATED", request.getAmount());
        eventService.publishEvent("order_created", event);
    }

    public void updateOrder(UpdateOrderRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(request.getStatus());
        orderRepository.save(order);

        OrderUpdatedEvent event = new OrderUpdatedEvent(request.getOrderId().toString(), request.getStatus());
        eventService.publishEvent("order_updated", event);
    }
}

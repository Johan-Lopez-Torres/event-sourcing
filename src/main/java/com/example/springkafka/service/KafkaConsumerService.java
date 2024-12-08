package com.example.springkafka.service;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "order-events", groupId = "order_events")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
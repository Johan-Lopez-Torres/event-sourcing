package com.example.springkafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishEvent(String eventType, Object event) {
        try {
            Map<String, Object> eventPayload = new HashMap<>();
            eventPayload.put("eventType", eventType);
            eventPayload.put("eventData", event);

            String payload = objectMapper.writeValueAsString(eventPayload);
            kafkaTemplate.send("order-events", payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing event", e);
        }
    }


}

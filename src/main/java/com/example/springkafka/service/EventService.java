package com.example.springkafka.service;

import com.example.springkafka.domain.Event;
import com.example.springkafka.repository.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final EventRepository eventRepository;


    public void publishEvent(String eventType, Object event) {
        try {
            Map<String, Object> eventPayload = new HashMap<>();
            eventPayload.put("eventType", eventType);
            eventPayload.put("eventData", event);

            String payload = objectMapper.writeValueAsString(eventPayload);
            String key = String.valueOf(UUID.randomUUID());

            // Save event to the database
            saveEvent(eventType, key, payload);

            kafkaTemplate.send("order-events", key, payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing event", e);
        }
    }


    public void saveEvent(String eventType, String aggregateId, String payload) {
        Event event = new Event();
        event.setEventType(eventType);
        event.setAggregateId(aggregateId);
        event.setPayload(payload);
        event.setTimestamp(LocalDateTime.now());
        eventRepository.save(event);
    }



}

package com.bejlka.foodservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
public class RabbitMQService {

    @Value("${rabbitmq.topic-exchange-food}")
    String TOPIC_EXCHANGE_FOOD;
    @Value("${rabbitmq.routing-key-food}")
    String ROUTING_KEY_FOOD;

    final RabbitTemplate rabbitTemplate;

    public void sendMessage(Object o) {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_FOOD, ROUTING_KEY_FOOD, o);
    }
}

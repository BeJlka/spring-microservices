package com.bejlka.foodservice.service;

import com.bejlka.foodservice.model.domain.entity.Order;
import com.bejlka.foodservice.model.dto.NotificationDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RabbitMQService {

    final OrderService orderService;
    final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.topic-exchange-food}")
    String TOPIC_EXCHANGE_FOOD;
    @Value("${rabbitmq.routing-key-food}")
    String ROUTING_KEY_FOOD;

    public void sendMessage(Long orderId) {
        log.info("RabbitMQ: " + orderId);
        Order order = orderService.find(orderId);
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setAmount(order.getAmount());
        notificationDTO.setEmail(order.getUser().getEmail());
        notificationDTO.setName(order.getUser().getName());
        notificationDTO.setOrderId(order.getId());
        notificationDTO.setStatus(order.getStatus().name());
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_FOOD, ROUTING_KEY_FOOD, notificationDTO);
    }
}

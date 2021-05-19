package com.bejlka.notificationservice.service;

import com.bejlka.notificationservice.domain.NotificationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class FoodMessageHandler {
    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = "${rabbitmq.queue-food}")
    public void receive(Message message) throws IOException {
        System.out.println(message);
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationDTO notificationDTO = objectMapper.readValue(message.getBody(), NotificationDTO.class);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(notificationDTO.getEmail());
        mailMessage.setFrom("ya@ya.com");

        mailMessage.setSubject("Заказ №" + notificationDTO.getOrderId());
        mailMessage.setText(notificationDTO.getMassage());

        javaMailSender.send(mailMessage);

    }
}

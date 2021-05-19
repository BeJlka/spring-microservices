package com.bejlka.notificationservice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
@RequiredArgsConstructor
@EnableRabbit
@Getter
public class RabbitMQConfig {

    @Value("${rabbitmq.queue-food}")
    private String QUEUE_FOOD;
    @Value("${rabbitmq.topic-exchange-food}")
    private String TOPIC_EXCHANGE_FOOD;
    @Value("${rabbitmq.routing-key-food}")
    private String ROUTING_KEY_FOOD;

    @Bean
    public TopicExchange foodExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_FOOD);
    }

    @Bean
    public Queue queueFood() {
        return new Queue(QUEUE_FOOD);
    }

    @Bean
    public Binding foodBinding() {
        return BindingBuilder
                .bind(queueFood())
                .to(foodExchange())
                .with(ROUTING_KEY_FOOD);
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}

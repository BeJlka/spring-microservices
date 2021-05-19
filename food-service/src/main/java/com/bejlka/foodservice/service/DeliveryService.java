package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.DeliveryBodyDTO;
import com.bejlka.foodservice.domain.dto.DeliveryDTO;
import com.bejlka.foodservice.domain.dto.NotificationDTO;
import com.bejlka.foodservice.domain.entity.Order;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.feign.DeliveryServiceClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryService {
    DeliveryServiceClient deliveryServiceClient;
    RabbitMQService rabbitMQService;

    public DeliveryDTO createDelivery(Order order) {
        DeliveryBodyDTO body = new DeliveryBodyDTO();
        body.setOrderId(order.getId());
        body.setUserId(order.getUser().getId());
        body.setRestaurantAddress(order.getRestaurant().getAddress());
        body.setDeliveryAddress(order.getUser().getAddress());
        body.setOrderDate(order.getOrderDate());
        return deliveryServiceClient.createDelivery(body);
    }

    public List<DeliveryDTO> deliveryAll(User user) {
        return deliveryServiceClient.deliveryAll(user.getId());
    }

    public DeliveryDTO delivery(Long id) {
        return deliveryServiceClient.delivery(id);
    }

    public DeliveryDTO updateStatusDelivery(User user, Long id) {
        DeliveryDTO deliveryDTO = deliveryServiceClient.updateStatusDelivery(id);

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEmail(user.getEmail());
        notificationDTO.setOrderId(deliveryDTO.getOrderId());
        notificationDTO.setTitle("Доставка");
        if (deliveryDTO.getStatus().equals("DELIVERY")) {
            notificationDTO.setMessage("Курьер уже направляется к вам");
            rabbitMQService.sendMessage(notificationDTO);
        }
        if (deliveryDTO.getStatus().equals("DONE")) {
            notificationDTO.setMessage("Курьер доставил заказ");
            rabbitMQService.sendMessage(notificationDTO);
        }
        return deliveryDTO;
    }
}

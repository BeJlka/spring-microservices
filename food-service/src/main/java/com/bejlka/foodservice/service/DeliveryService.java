package com.bejlka.foodservice.service;

import com.bejlka.foodservice.feign.DeliveryServiceClient;
import com.bejlka.foodservice.model.domain.entity.Order;
import com.bejlka.foodservice.model.domain.entity.User;
import com.bejlka.foodservice.model.dto.DeliveryBodyDTO;
import com.bejlka.foodservice.model.dto.DeliveryDTO;
import com.bejlka.foodservice.model.enums.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryService {

    OrderService orderService;
    DeliveryServiceClient deliveryServiceClient;

    public void createDeliveryCamunda(Long orderId) {
        log.info("Delivery create: " + orderId);
        Order order = orderService.find(orderId);
        DeliveryBodyDTO body = new DeliveryBodyDTO();
        body.setOrderId(order.getId());
        body.setUserId(order.getUser().getId());
        body.setRestaurantAddress(order.getRestaurant().getAddress());
        body.setDeliveryAddress(order.getUser().getAddress());
        body.setOrderDate(order.getOrderDate());
        DeliveryDTO delivery = deliveryServiceClient.createDelivery(body);

        if (delivery.getStatus().equals("RECEIVED")) {
            order.setStatus(Status.DELIVERY_RECEIVED);
            orderService.update(order);
        } else {
            throw new BpmnError("FAIL_DELIVERY");
        }
    }

    public void updateStatusDeliveryCamunda(Long orderId) {
        log.info("Update status delivery: " + orderId);
        Order order = orderService.find(orderId);
        if (!order.getStatus().equals(Status.DELIVERY_DONE) || !order.getStatus().equals(Status.DONE)) {
            DeliveryDTO deliveryDTO = deliveryServiceClient.updateStatusDelivery(orderId);
            if (deliveryDTO == null) {
                throw new BpmnError("FAIL_DELIVERY");
            }
            if (deliveryDTO.getStatus().equals("DELIVERY")) {
                order.setStatus(Status.DELIVERY);
            }
            if (deliveryDTO.getStatus().equals("DONE")) {
                order.setStatus(Status.DELIVERY_DONE);
            }
            orderService.update(order);
        }
    }

    public void cancelDelivery(Long orderId) {
        log.error("cancel delivery: " + orderId);
        Order order = orderService.find(orderId);
        order.setStatus(Status.FAIL_DELIVERY);
        orderService.update(order);
        deliveryServiceClient.cancelDelivery(orderId);
    }

    public List<DeliveryDTO> deliveryAll(User user) {
        return deliveryServiceClient.deliveryAll(user.getId());
    }

    public DeliveryDTO delivery(Long id) {
        return deliveryServiceClient.delivery(id);
    }
//
//    public DeliveryDTO updateStatusDelivery(User user, Long id) {
//        DeliveryDTO deliveryDTO = deliveryServiceClient.updateStatusDelivery(id);
//
////        NotificationDTO notificationDTO = new NotificationDTO();
////        notificationDTO.setEmail(user.getEmail());
////        notificationDTO.setOrderId(deliveryDTO.getOrderId());
////        notificationDTO.setTitle("Доставка");
////        if (deliveryDTO.getStatus().equals("DELIVERY")) {
////            notificationDTO.setMessage("Курьер уже направляется к вам");
////            rabbitMQService.sendMessage(notificationDTO);
////        }
////        if (deliveryDTO.getStatus().equals("DONE")) {
////            notificationDTO.setMessage("Курьер доставил заказ");
////            rabbitMQService.sendMessage(notificationDTO);
////        }
//        return deliveryDTO;
//    }
}

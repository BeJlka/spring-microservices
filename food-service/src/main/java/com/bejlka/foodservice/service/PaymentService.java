package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.NotificationDTO;
import com.bejlka.foodservice.domain.dto.PaymentBodyDTO;
import com.bejlka.foodservice.domain.dto.PaymentDTO;
import com.bejlka.foodservice.domain.entity.Order;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.domain.enums.Status;
import com.bejlka.foodservice.feign.PaymentServiceClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    PaymentServiceClient paymentServiceClient;
    RabbitMQService rabbitMQService;
    OrderService orderService;

    public PaymentDTO payment(Long id) {
        return paymentServiceClient.getPayment(id);
    }

    public List<PaymentDTO> paymentsAll(User user) {
        return paymentServiceClient.getPayments(user.getId());
    }

    public PaymentDTO createPayment(User user, Order order) {
        PaymentBodyDTO body = new PaymentBodyDTO();
        body.setUserId(user.getId());
        body.setOrderId(order.getId());
        return paymentServiceClient.createPayment(body);
    }

    public PaymentDTO updatePayment(Long id) {
        PaymentDTO paymentDTO = paymentServiceClient.updatePayment(id);
        Order order = orderService.find(paymentDTO.getOrderId());
        order.setStatus(Status.COOKING);

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEmail(order.getUser().getEmail());
        notificationDTO.setOrderId(order.getId());
        notificationDTO.setTitle("Оплата");
        notificationDTO.setMassage("Оплата прошла успешно и ресторан приступил к готовке");

        rabbitMQService.sendMessage(notificationDTO);
        orderService.update(order);
        return paymentDTO;
    }
}

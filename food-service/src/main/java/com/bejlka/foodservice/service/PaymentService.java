package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.PaymentDTO;
import com.bejlka.foodservice.domain.dto.PaymentServiceDTO;
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
    OrderService orderService;

    public PaymentDTO payment(User user) {
        return paymentServiceClient.getPayment(user.getId());
    }

    public List<PaymentDTO> paymentsAll(User user) {
        return paymentServiceClient.getPayments(user.getId());
    }

    public PaymentDTO createPayment(User user, Order order) {
        PaymentServiceDTO paymentServiceDTO = new PaymentServiceDTO();
        paymentServiceDTO.setUserId(user.getId());
        paymentServiceDTO.setOrderId(order.getId());
        return paymentServiceClient.createPayment(paymentServiceDTO);
    }

    public PaymentDTO updatePayment(Long id) {
        PaymentDTO paymentDTO = paymentServiceClient.updatePayment(id);
        Order order = orderService.find(paymentDTO.getOrderId());
        order.setStatus(Status.COOKING);
        orderService.update(order);
        return paymentDTO;
    }
}

package com.bejlka.foodservice.service;

import com.bejlka.foodservice.feign.PaymentServiceClient;
import com.bejlka.foodservice.model.domain.entity.Order;
import com.bejlka.foodservice.model.domain.entity.User;
import com.bejlka.foodservice.model.dto.PaymentBodyDTO;
import com.bejlka.foodservice.model.dto.PaymentDTO;
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
public class PaymentService {
    PaymentServiceClient paymentServiceClient;
    OrderService orderService;

    public PaymentDTO payment(Long id) {
        return paymentServiceClient.getPayment(id);
    }

    public List<PaymentDTO> paymentsAll(User user) {
        return paymentServiceClient.getPayments(user.getId());
    }

    public void createPaymentCamunda(Long orderId) {
        log.info("Payment: " + orderId);
        Order order = orderService.find(orderId);

        PaymentBodyDTO body = new PaymentBodyDTO();
        body.setOrderId(order.getId());
        body.setUserId(order.getUserId());
        PaymentDTO payment = paymentServiceClient.createPayment(body);
        if (payment.getStatus().equals("SUCCESS")) {
            order.setStatus(Status.COOKING);
            orderService.update(order);
        } else {
            order.setStatus(Status.FAIL);
            orderService.update(order);
            throw new BpmnError(payment.getStatus());
        }
    }

    public void errorPayment(Long orderId) {
        Order order = orderService.find(orderId);
        log.error("Fail payment: " + orderId);
        paymentServiceClient.cancelPayment(orderId);
        order.setStatus(Status.FAIL_PAYMENT);
        orderService.update(order);
    }

    public void cancelPayment(Long orderId) {
        log.info("cancel payment: " + orderId);
        paymentServiceClient.cancelPayment(orderId);
    }
}

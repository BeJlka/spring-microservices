package com.bejlka.foodservice.service;

import com.bejlka.foodservice.feign.PaymentServiceClient;
import com.bejlka.foodservice.model.domain.entity.Order;
import com.bejlka.foodservice.model.domain.entity.User;
import com.bejlka.foodservice.model.dto.NotificationDTO;
import com.bejlka.foodservice.model.dto.PaymentBodyDTO;
import com.bejlka.foodservice.model.dto.PaymentDTO;
import com.bejlka.foodservice.model.enums.Status;
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

    public PaymentDTO payment(Long id) {
        return paymentServiceClient.getPayment(id);
    }

    public List<PaymentDTO> paymentsAll(User user) {
        return paymentServiceClient.getPayments(user.getId());
    }

    public PaymentDTO createPayment(Long userId, Long orderId) {
        PaymentBodyDTO body = new PaymentBodyDTO();
        body.setUserId(userId);
        body.setOrderId(orderId);
        return paymentServiceClient.createPayment(body);
    }

    public PaymentDTO updatePayment(Long id) {
        return paymentServiceClient.updatePayment(id);
    }
}

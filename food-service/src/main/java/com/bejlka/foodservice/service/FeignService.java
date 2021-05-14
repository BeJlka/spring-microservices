package com.bejlka.foodservice.service;

import com.bejlka.foodservice.feign.DeliveryServiceClient;
import com.bejlka.foodservice.feign.PaymentServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeignService {

    private final DeliveryServiceClient deliveryServiceClient;
    private final PaymentServiceClient paymentServiceClient;


    public String getAll() {
        return "{\n\"delivery\": " + deliveryServiceClient.hello() + "\n\"payment\": " + paymentServiceClient.hello() + "\n}";
    }

    public String getDelivery() {
        return "{\n\"delivery\": \"" + deliveryServiceClient.hello() + "\"\n}";
    }

    public String getPayment() {
        return "{\n\"payment\": \"" + paymentServiceClient.hello() + "\"\n}";
    }
}

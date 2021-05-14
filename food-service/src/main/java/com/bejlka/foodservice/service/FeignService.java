package com.bejlka.foodservice.service;

import com.bejlka.foodservice.feign.DeliveryServiceClient;
import com.bejlka.foodservice.feign.PaymentServiceClient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeignService {

    DeliveryServiceClient deliveryServiceClient;
    PaymentServiceClient paymentServiceClient;


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

package com.bejlka.foodservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    String hello();
}

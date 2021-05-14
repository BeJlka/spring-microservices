package com.bejlka.foodservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "delivery-service")
public interface DeliveryServiceClient {

    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    String hello();
}

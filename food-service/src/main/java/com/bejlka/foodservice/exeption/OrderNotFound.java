package com.bejlka.foodservice.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OrderNotFound extends RuntimeException{

    public OrderNotFound(String message) {
        super(message);
    }
}

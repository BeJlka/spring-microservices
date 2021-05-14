package com.bejlka.foodservice.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NameBusy extends RuntimeException{

    public NameBusy(String message) {
        super(message);
    }

}
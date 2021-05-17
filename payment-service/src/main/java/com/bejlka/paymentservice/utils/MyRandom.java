package com.bejlka.paymentservice.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MyRandom {

    private final Random random;

    public MyRandom() {
        random = new Random();
    }

    public boolean getRandomBoolean() {
        return random.nextBoolean();
    }
}

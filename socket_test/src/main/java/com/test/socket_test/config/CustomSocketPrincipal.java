package com.test.socket_test.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.Principal;

public class CustomSocketPrincipal implements Principal {
    private final String name;
    @Getter
    private final String restaurantId;

    CustomSocketPrincipal(String name, String restaurantId) {
        this.name = name;
        this.restaurantId = restaurantId;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

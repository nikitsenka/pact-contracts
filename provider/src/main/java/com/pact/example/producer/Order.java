package com.pact.example.producer;

public class Order {

    private String id;

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

package com.pact.example.producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping
    private Flux<Order> getOrders() {
        return Flux.just(new Order("1"), new Order("2"));
    }

}
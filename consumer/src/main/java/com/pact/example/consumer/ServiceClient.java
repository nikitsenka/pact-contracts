package com.pact.example.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ServiceClient {

    @Autowired
    private WebClient webClient;

    public Flux<JsonNode> getOrders(String uri) {
        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(JsonNode.class);
    }
}

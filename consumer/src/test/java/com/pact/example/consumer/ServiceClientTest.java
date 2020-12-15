package com.pact.example.consumer;


import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "orders-provider", hostInterface = "localhost", port = "8081")
//@PactFolder("pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceClientTest {

    @Autowired
    private ServiceClient client;

    @Pact(consumer = "users-service", provider = "orders-service")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("Order service operates successfully")
                .uponReceiving("Request to get all orders")
                .path("/orders")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(Map.of(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .body("""
                          [
                            {"id": "1"},
                            {"id": "2"}
                          ]
                        """)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createPact")
    public void get_shouldSuccessfullyReturnAllOrders_ifOrdersExist() {
        Flux<JsonNode> orders = client.getOrders("http://localhost:8081/orders");
        StepVerifier
                .create(orders)
                .assertNext(o -> assertEquals("1", o.get("id").asText()))
                .assertNext(o -> assertEquals("2", o.get("id").asText()))
                .expectComplete()
                .verify();
    }

}
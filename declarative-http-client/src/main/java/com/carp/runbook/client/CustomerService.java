package com.carp.runbook.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

interface CustomerService {

    @GetExchange("/customers")
    Flux<ResponseEntity<Customer>> getCustomers();

    @GetExchange("/customers/{id}")
    Mono<ResponseEntity<Customer>> getCustomer(@PathVariable Long id);

    @PostExchange("/customers")
    Mono<ResponseEntity<Customer>> createCustomer(@RequestBody Customer customer);

    @PutExchange("/customers/{id}")
    Mono<ResponseEntity<Customer>> updateCustomer(@PathVariable Long id, @RequestBody Customer customer);

    @DeleteExchange("/customers/{id}")
    Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Long id);
}

@JsonIgnoreProperties(ignoreUnknown = true)
record Customer(String name, Instant createdAt, Instant lastModifiedAt) {

    public Customer(String name) {
        this(name, null, null);
    }
}

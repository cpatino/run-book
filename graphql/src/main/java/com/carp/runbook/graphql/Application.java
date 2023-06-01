package com.carp.runbook.graphql;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        WebClient webClient = WebClient.builder().build();
        HttpGraphQlClient graphQlClient = HttpGraphQlClient.builder(webClient)
                .url("http://localhost:8080/graphql")
                .build();

        graphQlClient.document("query { customer(id: 1) { id, name, email, age, company } }")
                .retrieve("customer")
                .toEntity(Customer.class)
                .doOnNext(customer -> LOGGER.info("Customer: {}", customer))
                .block();

        graphQlClient.document("mutation { addCustomer(name: \"Jane Doe\", email: \"email\", age: 42, company: \"Fake Inc.\") { id } }")
                .execute()
                .doOnNext(response -> LOGGER.info("Response: {}", response))
                .block();

        graphQlClient.document("query { customers { name email age company } }")
                .retrieve("customers")
                .toEntityList(Customer.class)
                .doOnNext(customers -> LOGGER.info("Customers: {}", customers))
                .block();
    }
}

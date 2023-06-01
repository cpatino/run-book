package com.carp.runbook.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class CustomerController {

    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();

    public CustomerController() {
        customers.put(1L, new Customer(1L, "John Doe", "john@fake.com", 42, "Fake Inc."));
    }

    @QueryMapping
    public List<Customer> customers() {
        return List.copyOf(customers.values());
    }

    @QueryMapping
    public Customer customer(@Argument Long id) {
        return customers.get(id);
    }

    @MutationMapping
    public Customer addCustomer(@Argument String name, @Argument String email, @Argument Integer age, @Argument String company) {
        Customer customer = new Customer((long) customers.size() + 1, name, email, age, company);
        customers.put(customer.id(), customer);
        return customer;
    }
}

record Customer(Long id, String name, String email, Integer age, String company) {
}

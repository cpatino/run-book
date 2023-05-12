package com.carp.tracing;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
class CustomerService {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CustomerService.class);
    private final List<Customer> customers;

    CustomerService() {
        customers = Collections.synchronizedList(new LinkedList<>());
        customers.add(new Customer(1, "John"));
        customers.add(new Customer(2, "Jane"));
    }

    public List<Customer> findAll() {
        LOG.info("findAll");
        return customers;
    }

    public void save(Customer customer) {
        LOG.info("save: {}", customer);
        customers.add(customer);
    }
}

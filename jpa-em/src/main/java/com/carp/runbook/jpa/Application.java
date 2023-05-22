package com.carp.runbook.jpa;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Application.class);

    private final CustomerRepository customerRepository;

    public Application(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("All customers -> {}", customerRepository.findAll());
        log.info("Customer id 10001 -> {}", customerRepository.findById(10001L));
        log.info("Deleting 10003 -> ");
        customerRepository.deleteById(10003L);
        log.info("Inserting -> {}", customerRepository.save(new Customer("DOLOR")));
        Customer customerToBeUpdated = customerRepository.findById(10002L);
        customerToBeUpdated.setName("SIT");
        log.info("Updating 10002 -> {}", customerRepository.save(customerToBeUpdated));
        log.info("All customers -> {}", customerRepository.findAll());
        log.info("All customers with id > 10000 -> {}", customerRepository.findAllGreaterThan10000());
    }
}

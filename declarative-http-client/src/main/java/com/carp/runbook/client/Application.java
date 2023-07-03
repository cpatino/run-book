package com.carp.runbook.client;

import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(Application.class);

    private final CustomerService customerService;

    public Application(CustomerService customerService) {
        this.customerService = customerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("Customers {}", customerService.getCustomers().toStream().toList());
        LOGGER.info("Customer {}", customerService.getCustomer(10001L).block());
        LOGGER.info("Created customer {}", customerService.createCustomer(new Customer("John Doe")).block());
        LOGGER.info("Updated customer {}", customerService.updateCustomer(10001L, new Customer("Jane Doe")).block());
        LOGGER.info("Deleting customer {}", customerService.deleteCustomer(10002L).block());
    }
}

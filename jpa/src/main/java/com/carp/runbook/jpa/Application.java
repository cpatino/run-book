package com.carp.runbook.jpa;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Application.class);

    private final CustomerJpaRepository customerJpaRepository;

    public Application(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("All customers -> {}", customerJpaRepository.findAll());
        log.info("Customer id 10001 -> {}", customerJpaRepository.findById(10001L));
        log.info("Deleting 10003 -> ");
        customerJpaRepository.deleteById(10003L);
        log.info("Inserting -> {}", customerJpaRepository.save(new Customer("DOLOR")));
        Customer customerToBeUpdated = customerJpaRepository.findById(10002L).orElseThrow();
        customerToBeUpdated.setName("SIT");
        log.info("Updating 10002 -> {}", customerJpaRepository.save(customerToBeUpdated));
        log.info("All customers -> {}", customerJpaRepository.findAll());
        log.info("All customers with id > 10000 -> {}", customerJpaRepository.findAllWithIdGreaterThan10000());
    }
}

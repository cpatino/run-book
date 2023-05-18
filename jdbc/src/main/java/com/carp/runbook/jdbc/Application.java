package com.carp.runbook.jdbc;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Application.class);
    private final CustomerJdbcDao customerJdbcDao;

    public Application(CustomerJdbcDao customerJdbcDao) {
        this.customerJdbcDao = customerJdbcDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("All customers -> {}", customerJdbcDao.findAll());
        log.info("Customer id 10001 -> {}", customerJdbcDao.findById(10001L));
        log.info("Deleting 10003 -> No of Rows Deleted - {}", customerJdbcDao.deleteById(10003L));
        log.info("Inserting 10004 -> No of Rows Inserted - {}", customerJdbcDao.insert(new Customer(10004L, "DOLOR", Instant.now())));
        log.info("Updating 10002 -> No of Rows Updated - {}", customerJdbcDao.update(new Customer(10002L, "SIT", Instant.now())));
        log.info("All customers -> {}", customerJdbcDao.findAll());
    }
}

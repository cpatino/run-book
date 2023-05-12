package com.carp.tracing;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
  
  private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CustomerController.class);
  
  private final CustomerService customerService;
  
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }
  
  @GetMapping
  public List<Customer> findAll() {
    LOG.info("findAll");
    return customerService.findAll();
  }
}

@Service
class CustomerService {
  
  private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(CustomerService.class);
  
  public List<Customer> findAll() {
    LOG.info("findAll");
    return List.of(new Customer(1, "John"), new Customer(2, "Jane"));
  }
}

record Customer(long id, String name) {
}


package com.carp.runbook.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.id > 10000")
    List<Customer> findAllWithIdGreaterThan10000();
}

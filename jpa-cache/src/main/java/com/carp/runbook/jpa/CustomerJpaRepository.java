package com.carp.runbook.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "customers")
interface CustomerJpaRepository extends JpaRepository<Customer, Long> {
}

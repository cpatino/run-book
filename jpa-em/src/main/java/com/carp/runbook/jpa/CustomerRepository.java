package com.carp.runbook.jpa;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CustomerRepository {

    private final EntityManager entityManager;

    public CustomerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Customer> findAll() {
        return entityManager.createQuery("select c from Customer c", Customer.class).getResultList();
    }

    public Customer findById(long id) {
        return entityManager.find(Customer.class, id);
    }

    public void deleteById(long id) {
        Customer customer = findById(id);
        entityManager.remove(customer);
    }

    public Customer save(Customer customer) {
        return entityManager.merge(customer);
    }

    public List<Customer> findAllGreaterThan10000() {
        return entityManager.createQuery("select c from Customer c where c.id > 10000", Customer.class).getResultList();
    }
}

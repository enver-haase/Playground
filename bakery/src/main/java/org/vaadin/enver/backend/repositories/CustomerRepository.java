package org.vaadin.enver.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.vaadin.enver.backend.data.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

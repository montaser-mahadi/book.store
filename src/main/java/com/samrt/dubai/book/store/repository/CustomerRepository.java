package com.samrt.dubai.book.store.repository;

import com.samrt.dubai.book.store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer>  findByName(String name);
    Customer getCustomerByEmailAndName(String email, String name);
}

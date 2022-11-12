package com.samrt.dubai.book.store.controller;


import com.samrt.dubai.book.store.entity.Customer;
import com.samrt.dubai.book.store.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping(path = "/newCustomer")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer)
    {
        try {
            String massage = "";
            Optional<Customer> isFound = customerRepository.findByName(customer.getName());
            if(isFound.isPresent())
            {
                massage = "Username already exist!";
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .body(massage);
            }else {
                massage="customer saved ";
                Customer newCustomer = customerRepository.save(new Customer(customer.getName(), customer.getEmail()));
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(massage);
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

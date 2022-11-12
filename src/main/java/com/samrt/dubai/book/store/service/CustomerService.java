package com.samrt.dubai.book.store.service;

import com.samrt.dubai.book.store.entity.Customer;
import com.samrt.dubai.book.store.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer){

        return customerRepository.save(customer);
    }

    public Integer isCustomerPresent(Customer customer) {
        Customer customer1 = customerRepository.getCustomerByEmailAndName(customer.getEmail(), customer.getName());
        return customer1 != null ? customer1.getId() : null;
    }

    public List<Customer> getCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        System.out.println("Getting data from DB : " + customerList);
        return customerList;
    }
    public void deleteById(Customer customer)
    {
        customerRepository.delete(customer);
    }
}
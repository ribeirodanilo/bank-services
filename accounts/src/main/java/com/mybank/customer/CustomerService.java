package com.mybank.customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        log.info("Saving new Customer {} to the database", customer.getName());
        return customerRepository.save(customer);
    }

}

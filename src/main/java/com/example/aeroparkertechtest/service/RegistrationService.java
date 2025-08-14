package com.example.aeroparkertechtest.service;

import com.example.aeroparkertechtest.entity.Customer;
import com.example.aeroparkertechtest.model.CustomerRequest;
import com.example.aeroparkertechtest.repository.CustomerRepository;
import com.example.aeroparkertechtest.util.ConverterUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationService {
    private final CustomerRepository customerRepository;

    public Customer registerCustomer(CustomerRequest customerRequest) {

        Customer customer = ConverterUtils.toCustomer(customerRequest);

        customerRepository.save(customer);
        return customer;
    }
}

package com.example.aeroparkertechtest.util;

import com.example.aeroparkertechtest.entity.Customer;
import com.example.aeroparkertechtest.model.CustomerRequest;

import java.time.Instant;

public final class ConverterUtils {
    private ConverterUtils() {

    }

    public static Customer toCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmailAddress(customerRequest.getEmailAddress());
        customer.setTitle(customerRequest.getTitle());
        customer.setAddressLine1(customerRequest.getAddressLine1());
        customer.setAddressLine2(customerRequest.getAddressLine2());
        customer.setCity(customerRequest.getCity());
        customer.setPostcode(customerRequest.getPostcode());
        customer.setRegistered(Instant.now());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        return customer;
    }

}

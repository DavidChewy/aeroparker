package com.example.aeroparkertechtest.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    @NotBlank @Email @Size(max=255)
    private String emailAddress;

    @NotBlank @Size(max=5)
    private String title;

    @NotBlank @Size(max=50)
    private String firstName;

    @NotBlank @Size(max=50)
    private String lastName;

    @NotBlank @Size(max=255)
    private String addressLine1;

    @Size(max=255)
    private String addressLine2;

    @Size(max=255) private String city;

    @NotBlank @Size(max=10)
    private String postcode;

    @Size(max=20)
    private String phoneNumber;

}


package com.example.aeroparkertechtest.customer;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;





// Customer entity represents a row in the "customers" table.
// It maps form inputs to database columns via JPA annotations
// and enforces validation rules (e.g., required fields, max length, email format)
// before data is persisted.

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "customers",
        uniqueConstraints = @UniqueConstraint(name="uk_customers_email", columnNames="email_address"))
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "timestamp")
    private Instant registered = Instant.now();

    @NotBlank @Email @Size(max=255)
    @Column(name="email_address", nullable=false, length=255)
    private String emailAddress;

    @NotBlank @Size(max=5)
    @Column(nullable=false, length=5)
    private String title;

    @NotBlank @Size(max=50) @Column(name="first_name", nullable=false, length=50)
    private String firstName;

    @NotBlank @Size(max=50) @Column(name="last_name", nullable=false, length=50)
    private String lastName;

    @NotBlank @Size(max=255) @Column(name="address_line_1", nullable=false, length=255)
    private String addressLine1;

    @Size(max=255) @Column(name="address_line_2", length=255)
    private String addressLine2;

    @Size(max=255) private String city;

    @NotBlank @Size(max=10) @Column(nullable=false, length=10)
    private String postcode;

    @Size(max=20) @Column(name="phone_number", length=20)
    private String phoneNumber;

    @PrePersist @PreUpdate
    void normalize() { if (emailAddress != null) emailAddress = emailAddress.toLowerCase().trim(); }

}


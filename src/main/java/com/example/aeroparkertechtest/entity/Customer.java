package com.example.aeroparkertechtest.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;

// Customer entity represents a row in the "customers" table.
// It maps form inputs to database columns via JPA annotations


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "timestamp")
    private Instant registered = Instant.now();

    @Column(name="email_address", nullable=false)
    private String emailAddress;

    @Column(nullable=false)
    private String title;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(name="address_line_1", nullable=false)
    private String addressLine1;

    @Column(name="address_line_2")
    private String addressLine2;

    @Size(max=255) private String city;

    @Column(nullable=false)
    private String postcode;

    @Column(name="phone_number")
    private String phoneNumber;


    //before you insert, it's changing the email to lowercase
    @PrePersist @PreUpdate
    void normalize() { if (emailAddress != null) emailAddress = emailAddress.toLowerCase().trim(); }

}


package com.example.aeroparkertechtest.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private static CustomerRequest valid() {
        CustomerRequest r = new CustomerRequest();
        r.setEmailAddress("john.doe@example.com");
        r.setTitle("Mr");
        r.setFirstName("John");
        r.setLastName("Doe");
        r.setAddressLine1("123 Main Street");
        r.setAddressLine2("Apt 4");
        r.setCity("London");
        r.setPostcode("AB12CD");
        r.setPhoneNumber("0123456789");
        return r;
    }

    private static String ofLen(int n) {
        return "x".repeat(Math.max(0, n));
    }

    @Test
    void validCustomerRequest_hasNoViolations() {
        Set<ConstraintViolation<CustomerRequest>> v = validator.validate(valid());
        assertThat(v).isEmpty();
    }

    static Stream<String> invalidEmails() {
        return Stream.of(
                null, "", " ", "noatsign", "john@", "@example.com", "john@@example.com", "john@example.", "john..doe@example.com", "john doe@example.com"
        );
    }

    @ParameterizedTest
    @MethodSource("invalidEmails")
    void email_requiredAndFormat_invalid(String email) {
        CustomerRequest r = valid();
        r.setEmailAddress(email);
        Set<ConstraintViolation<CustomerRequest>> v = validator.validate(r);
        assertThat(v).anyMatch(cv -> cv.getPropertyPath().toString().equals("emailAddress"));
    }

    static Stream<String> invalidBlanks() {
        return Stream.of(null, "", "   ");
    }

    @ParameterizedTest
    @MethodSource("invalidBlanks")
    void title_required(String val) {
        CustomerRequest r = valid();
        r.setTitle(val);
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("title"));
    }

    @Test
    void title_length() {
        CustomerRequest r = valid();
        r.setTitle(ofLen(5));
        assertThat(validator.validate(r)).isEmpty();

        r.setTitle(ofLen(6));
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("title"));
    }


    @ParameterizedTest
    @MethodSource("invalidBlanks")
    void firstName_required(String val) {
        CustomerRequest r = valid();
        r.setFirstName(val);
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("firstName"));
    }

    @Test
    void firstName_length() {
        CustomerRequest r = valid();
        r.setFirstName(ofLen(50));
        assertThat(validator.validate(r)).isEmpty();

        r.setFirstName(ofLen(51));
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("firstName"));
    }

    @ParameterizedTest
    @MethodSource("invalidBlanks")
    void lastName_required(String val) {
        CustomerRequest r = valid();
        r.setLastName(val);
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("lastName"));
    }

    @Test
    void lastName_length() {
        CustomerRequest r = valid();
        r.setLastName(ofLen(50));
        assertThat(validator.validate(r)).isEmpty();

        r.setLastName(ofLen(51));
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("lastName"));
    }

    @ParameterizedTest
    @MethodSource("invalidBlanks")
    void addressLine1_required(String val) {
        CustomerRequest r = valid();
        r.setAddressLine1(val);
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("addressLine1"));
    }

    @Test
    void addressLine1_length() {
        CustomerRequest r = valid();
        r.setAddressLine1(ofLen(255));
        assertThat(validator.validate(r)).isEmpty();

        r.setAddressLine1(ofLen(256));
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("addressLine1"));
    }

    @Test
    void addressLine2_optional() {
        CustomerRequest r = valid();
        r.setAddressLine2(null);
        assertThat(validator.validate(r)).isEmpty();

        r.setAddressLine2("");
        assertThat(validator.validate(r)).isEmpty();

        r.setAddressLine2(ofLen(256));
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("addressLine2"));
    }

    @Test
    void city_optional() {
        CustomerRequest r = valid();
        r.setCity(null);
        assertThat(validator.validate(r)).isEmpty();

        r.setCity("");
        assertThat(validator.validate(r)).isEmpty();

        r.setCity(ofLen(256));
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("city"));
    }

    @ParameterizedTest
    @MethodSource("invalidBlanks")
    void postcode_required(String val) {
        CustomerRequest r = valid();
        r.setPostcode(val);
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("postcode"));
    }

    @Test
    void postcode_length() {
        CustomerRequest r = valid();
        r.setPostcode(ofLen(10));
        assertThat(validator.validate(r)).isEmpty();

        r.setPostcode(ofLen(11));
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("postcode"));
    }

    @Test
    void phone_optional() {
        CustomerRequest r = valid();
        r.setPhoneNumber(null);
        assertThat(validator.validate(r)).isEmpty();

        r.setPhoneNumber("");
        assertThat(validator.validate(r)).isEmpty();

        r.setPhoneNumber(ofLen(21));
        assertThat(validator.validate(r))
                .anyMatch(cv -> cv.getPropertyPath().toString().equals("phoneNumber"));
    }


    @Test
    void multipleViolations() {
        CustomerRequest r = valid();
        r.setEmailAddress("bad");
        r.setFirstName(" "); // blank
        r.setPostcode(ofLen(11));
        Set<ConstraintViolation<CustomerRequest>> v = validator.validate(r);
        List<String> fields =
                v.stream().map(cv -> cv.getPropertyPath().toString()).toList();

        assertThat(fields).contains("emailAddress", "firstName", "postcode");
    }
}

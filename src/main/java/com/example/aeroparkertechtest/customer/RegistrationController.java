package com.example.aeroparkertechtest.customer;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {
    private final CustomerRepository customerRepository;

    public RegistrationController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("customer", new Customer());
        return "registration";
    }

    @PostMapping("/registration")
    public String submit(@Valid @ModelAttribute("customer") Customer customer,
                         BindingResult errors, Model model) {
        if (errors.hasErrors()) return "registration";

        try {
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException ex) {
            errors.rejectValue("emailAddress", "duplicate", "Email address is already registered");
            return "registration";
        }
        model.addAttribute("name", customer.getFirstName());
        model.addAttribute("email", customer.getEmailAddress());
        return "success";
    }
}

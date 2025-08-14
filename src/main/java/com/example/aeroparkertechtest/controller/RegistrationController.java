package com.example.aeroparkertechtest.controller;
import com.example.aeroparkertechtest.entity.Customer;
import com.example.aeroparkertechtest.model.CustomerRequest;
import com.example.aeroparkertechtest.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("customer", new Customer());
        return "registration";
    }

    @PostMapping("/registration")
    public String submit(@Valid @ModelAttribute("customer") CustomerRequest customer,
                         BindingResult errors, Model model) {
        if (errors.hasErrors()) return "registration";

        try {
            registrationService.registerCustomer(customer);
        } catch (DataIntegrityViolationException ex) {
            errors.rejectValue("emailAddress", "duplicate", "Email address is already registered");
            return "registration";
        }
        model.addAttribute("name", customer.getFirstName());
        model.addAttribute("email", customer.getEmailAddress());
        return "success";
    }
}

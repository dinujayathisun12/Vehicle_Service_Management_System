
package com.vehicle.vehicle_service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public String showCustomers(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("customer", new Customer());
        return "customers";
    }

    @PostMapping("/customers/save")
    public String saveCustomer(Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        model.addAttribute("customer", customer);
        model.addAttribute("customers", customerRepository.findAll());

        return "customers";
    }

    @PostMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {

        customerRepository.deleteById(id);

        return "redirect:/customers";
    }
}

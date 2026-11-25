
package com.vehicle.vehicle_service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BillingController {

    private final BillingRepository billingRepository;

    public BillingController(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @GetMapping("/billing")
    public String showBilling(Model model) {
        model.addAttribute("billings", billingRepository.findAll());
        model.addAttribute("billing", new Billing());
        return "billing";
    }

    @PostMapping("/billing/save")
    public String saveBilling(Billing billing) {
        billingRepository.save(billing);
        return "redirect:/billing";
    }

    @GetMapping("/billing/edit/{id}")
    public String editBilling(@PathVariable Long id, Model model) {

        Billing billing = billingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid billing ID"));

        model.addAttribute("billing", billing);
        model.addAttribute("billings", billingRepository.findAll());

        return "billing";
    }

    @PostMapping("/billing/delete/{id}")
    public String deleteBilling(@PathVariable Long id) {
        billingRepository.deleteById(id);
        return "redirect:/billing";
    }
}

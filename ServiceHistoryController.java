
package com.vehicle.vehicle_service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ServiceHistoryController {

    private final ServiceHistoryRepository historyRepository;

    public ServiceHistoryController(ServiceHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GetMapping("/service-history")
    public String showHistory(Model model) {
        model.addAttribute("histories", historyRepository.findAll());
        model.addAttribute("history", new ServiceHistory());
        return "service-history";
    }

    @PostMapping("/service-history/save")
    public String saveHistory(ServiceHistory history) {
        historyRepository.save(history);
        return "redirect:/service-history";
    }

    @GetMapping("/service-history/edit/{id}")
    public String editHistory(@PathVariable Long id, Model model) {

        ServiceHistory history = historyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid history ID"));

        model.addAttribute("history", history);
        model.addAttribute("histories", historyRepository.findAll());

        return "service-history";
    }

    @PostMapping("/service-history/delete/{id}")
    public String deleteHistory(@PathVariable Long id) {
        historyRepository.deleteById(id);
        return "redirect:/service-history";
    }
}
